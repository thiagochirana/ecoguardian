function realizarLogin() {
    var username = document.getElementById("cpf-login").value;
    var password = document.getElementById("senha-login").value;

    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ "cpf": username, "senha": password })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data.successo) {
                console.log("Sucesso!!");
                window.location.href = "/dashboard";
            } else {
                console.log("Deu ruim!");
                var errorMessage = document.getElementById("error-message");
                errorMessage.textContent = data.mensagem;
                errorMessage.classList.remove("hidden");
            }
        })
        .catch(error => {
            console.error("Erro na solicitação:", error);
        });
}
