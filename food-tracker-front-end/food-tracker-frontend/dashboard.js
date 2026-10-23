window.onload = async () => {

    const res = await fetch("http://localhost:8080/products/stats");
    const data = await res.json();

    document.getElementById("totali").textContent = data.totali;
    document.getElementById("scaduti").textContent = data.scaduti;
    document.getElementById("consumati").textContent = data.consumati;

    createChart(data);
    generateTips(data);
};

function createChart(data) {

    const ctx = document.getElementById("pieChart");

    new Chart(ctx, {
        type: "doughnut",
        data: {
            labels: ["Consumati", "Scaduti"],
            datasets: [{
                data: [data.consumati, data.scaduti]
            }]
        },
        options: {
            plugins: {
                legend: {
                    position: "bottom"
                }
            }
        }
    });
}

function generateTips(data) {

    const tips = document.getElementById("tips");
    tips.innerHTML = "";

    if (data.scaduti > data.consumati) {
        tips.innerHTML += "<li>⚠️ Hai molti sprechi, prova a controllare le scadenze più spesso</li>";
    }

    if (data.consumati > data.scaduti) {
        tips.innerHTML += "<li>👏 Ottimo! Stai consumando bene i prodotti</li>";
    }

    if (data.scaduti === 0) {
        tips.innerHTML += "<li>🎉 Nessuno spreco! Continua così</li>";
    }

    if (data.totali > 20) {
        tips.innerHTML += "<li>📦 Hai molti prodotti, organizza meglio il frigo</li>";
    }
}
