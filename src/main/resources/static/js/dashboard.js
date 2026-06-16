const token =
    localStorage.getItem("jwt");
if (!token) {

    window.location.href = "/";
}
async function loadData() {
    const region = document.getElementById("wojewodztwo").value;
    const yearFrom = document.getElementById("rokOd").value;
    const yearTo = document.getElementById("rokDo").value;

    const token = localStorage.getItem("jwt");

    const url = new URL("/api/data/json", window.location.origin);

    if (region) url.searchParams.append("region", region);
    if (yearFrom) url.searchParams.append("yearFrom", yearFrom);
    if (yearTo) url.searchParams.append("yearTo", yearTo);

    const response = await fetch(url, {
        headers: {
            Authorization: "Bearer " + token
        }
    });

    const data = await response.json();

    fillTable(data);
    drawCharts(data);
    loadCorrelation();
}

function fillTable(data) {

    const tbody =
        document.querySelector(
            "#resultsTable tbody");

    tbody.innerHTML = "";

    data.forEach(row => {

        tbody.innerHTML += `
            <tr>
                <td>${row.rok}</td>
                <td>${row.kwartal}</td>
                <td>${row.wojewodztwo}</td>
                <td>${row.sredniaCenaMieszkania}</td>
                <td>${row.sredniaStopaWKwarcie}</td>
            </tr>
        `;
    });
}
let priceChart;
let rateChart;

function drawCharts(data) {

    const labels = data.map(x => x.rok + "-Q" + x.kwartal);

    const prices = data.map(x => x.sredniaCenaMieszkania);

    const rates = data.map(x => x.sredniaStopaWKwarcie);

    if (priceChart) priceChart.destroy();
    if (rateChart) rateChart.destroy();

    priceChart = new Chart(
        document.getElementById("priceChart"),
        {
            type: "line",
            data: {
                labels,
                datasets: [{
                    label: "Cena mieszkania",
                    data: prices
                }]
            }
        }
    );

    rateChart = new Chart(
        document.getElementById("rateChart"),
        {
            type: "line",
            data: {
                labels,
                datasets: [{
                    label: "Stopa procentowa",
                    data: rates
                }]
            }
        }
    );
}
async function loadCorrelation() {

    const region =
        document.getElementById(
            "wojewodztwo").value;

    const response =
        await fetch(
            "/api/analytics/correlation/region?region="
            + region,
            {
                headers: {
                    Authorization:
                        "Bearer " + token
                }
            });

    const correlation =
        await response.json();

    document.getElementById(
        "correlationResult")
        .innerText =
        correlation.correlationValue;
}
async function downloadJson() {
    const token = localStorage.getItem("jwt");

    const response = await fetch("/api/data/json/download", {
        headers: {
            Authorization: "Bearer " + token
        }
    });

    if (!response.ok) {
        alert("Błąd pobierania: " + response.status);
        return;
    }

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;
    a.download = "fakty.json";
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);
}
async function downloadXml() {
    const token = localStorage.getItem("jwt");

    const response = await fetch("/api/data/xml/download", {
        headers: {
            Authorization: "Bearer " + token
        }
    });

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;
    a.download = "fakty.xml";
    document.body.appendChild(a);
    a.click();
    a.remove();
}
function parseJwt(token) {
    return JSON.parse(
        atob(token.split('.')[1])
    );
}
const payload = parseJwt(token);
const role = payload.role;
if(role === "ROLE_ADMIN") {

    document.getElementById(
        "adminSection")
        .style.display = "block";
}
async function refreshData() {

    const response =
        await fetch(
            "/api/admin/refresh",
            {
                method: "POST",

                headers: {
                    Authorization:
                        "Bearer " + token
                }
            });

    alert(
        await response.text());
}