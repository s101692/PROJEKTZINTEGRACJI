document
    .getElementById("loginForm")
    .addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();

            const username =
                document.getElementById(
                    "username").value;

            const password =
                document.getElementById(
                    "password").value;

            try {

                const response =
                    await fetch(
                        "/api/auth/login",
                        {
                            method: "POST",
                            headers: {
                                "Content-Type":
                                    "application/json"
                            },
                            body: JSON.stringify({
                                username,
                                password
                            })
                        });

                if (!response.ok) {
                    throw new Error(
                        "Niepoprawne dane logowania");
                }

                const data =
                    await response.json();

                localStorage.setItem(
                    "jwt",
                    data.token);

                window.location.href =
                    "/dashboard";

            } catch (error) {

                alert(error.message);
            }
        });