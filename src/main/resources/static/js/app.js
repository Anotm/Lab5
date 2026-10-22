document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("create-addressbook-form");
    if (form) form.addEventListener("submit", async (e) => {
        e.preventDefault();                       // stop the full-page POST
        const res = await fetch("/api/addressBooks", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: "{}"
        });
        const book = await res.json();
        const id = book._links.self.href.split("/").pop();   // see note below
        // append a new <tr> to the table with a link to /addressbook/{id}
    });
});