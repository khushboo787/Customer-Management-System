document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const errorMessageElement = document.getElementById('error-message');

    fetch('${pageContext.request.contextPath}/login', { // Adjust if needed
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            email: email,
            password: password,
        }),
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        } else {
            return response.text().then(text => {
                errorMessageElement.textContent = text;
            });
        }
    })
    .catch(error => {
        errorMessageElement.textContent = 'An error occurred. Please try again.';
    });
});
