

let allProducts = [];
let selectedProduct = null;

window.onload = async () => {
    const res = await fetch("http://localhost:8080/products");
    allProducts = await res.json();
    renderCategories();
};

// ---------------- CATEGORIE ----------------

function renderCategories() {

    const categoryView = document.getElementById("categoryView");
    categoryView.innerHTML = "";

    const categories = [...new Set(allProducts.map(p => p.categoria))];

    categories.forEach(cat => {

        if (!cat) return;

        const count = allProducts.filter(p => p.categoria === cat).length;

        const card = document.createElement("div");
        card.classList.add("category-card");

        card.innerHTML = `
            <h3>${getIcon(cat)} ${cat}</h3>
            <p>${count} prodotti</p>
        `;

        card.onclick = () => showProducts(cat);

        categoryView.appendChild(card);
    });
}

function getIcon(cat) {
    switch(cat.toLowerCase()) {
        case "latticini": return "🥛";
        case "carne": return "🥩";
        case "frutta": return "🍎";
        case "verdura": return "🥦";
        case "bevande": return "🥤";
        default: return "📦";
    }
}

// ---------------- PRODOTTI ----------------

function showProducts(category) {

    document.getElementById("categoryView").style.display = "none";
    document.getElementById("productView").style.display = "block";

    const list = document.getElementById("productList");
    list.innerHTML = "";

    const today = new Date();
    today.setHours(0,0,0,0);

    const filtered = allProducts.filter(p => p.categoria === category);

    filtered.forEach(p => {

        const card = document.createElement("div");
        card.classList.add("product-card");

        const expiry = new Date(p.dataScadenza);
        const diff = Math.floor((expiry - today)/(1000*60*60*24));

        if (diff < 0) card.classList.add("expired");
        else if (diff <= 3) card.classList.add("expiring");
        else card.classList.add("ok");

        card.innerHTML = `
            <button class="delete-btn">✕</button>
            <button class="consume-btn">✔</button>

            <h3>${p.nome}</h3>
            <p>Quantità: ${p.quantita}</p>
            <p>Scadenza: ${p.dataScadenza}</p>
        `;

        // CLICK CARD
        card.onclick = () => openModal(p);
        
        console.log("CLICK CONSUMATO");

        // ✔ CONSUMATO
        card.querySelector(".consume-btn").onclick = async (e) => {
            e.stopPropagation();

            await fetch(`http://localhost:8080/products/${p.id}/consume`, {
                method: "PUT"
            });

            location.reload();
        };

        // ✕ DELETE
        card.querySelector(".delete-btn").onclick = async (e) => {
            e.stopPropagation();

            await fetch(`http://localhost:8080/products/${p.id}`, {
                method: "DELETE"
            });

            card.classList.add("fade-out");

            setTimeout(() => {
                allProducts = allProducts.filter(prod => prod.id !== p.id);
                showProducts(category);
            }, 300);
        };

        list.appendChild(card);
    });
}

// ---------------- MODAL ----------------

function openModal(product) {

    selectedProduct = product;

    document.getElementById("productModal").style.display = "flex";
    document.getElementById("modalNome").textContent = product.nome;
    document.getElementById("noteInput").value = product.note || "";

    const list = document.getElementById("modalDetails");

    list.innerHTML = `
        <li>Categoria: ${product.categoria}</li>
        <li>Quantità: ${product.quantita}</li>
        <li>Scadenza: ${product.dataScadenza}</li>
        <li>Inserito il: ${product.dataInserimento || "N/A"}</li>
    `;
}

function closeModal() {
    document.getElementById("productModal").style.display = "none";
}

// ---------------- AZIONI ----------------

async function removeOne() {

    await fetch(`http://localhost:8080/products/${selectedProduct.id}/decrease`, {
        method: "PUT"
    });

    closeModal();
    location.reload();
}

async function addProduct() {

    const prodotto = {
        nome: document.getElementById("nome").value,
        categoria: document.getElementById("categoria").value,
        quantita: parseInt(document.getElementById("quantita").value) || 0,
        dataScadenza: document.getElementById("scadenza").value
    };

    await fetch("http://localhost:8080/products", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(prodotto)
    });

    alert("Prodotto aggiunto!");
}

async function saveNote() {

    const updated = {
        ...selectedProduct,
        note: document.getElementById("noteInput").value
    };

    await fetch(`http://localhost:8080/products/${selectedProduct.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updated)
    });

    alert("Note salvate!");
    closeModal();
    location.reload();
}

async function saveEmail() {
    const email = document.getElementById("email").value;

    await fetch("http://localhost:8080/email", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email: email })
    });

    document.getElementById("emailSaved").textContent = "Email salvata!";
}

// ---------------- NAV ----------------

function showCategories() {
    document.getElementById("categoryView").style.display = "grid";
    document.getElementById("productView").style.display = "none";
}
