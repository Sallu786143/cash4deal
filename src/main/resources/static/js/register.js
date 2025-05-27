function submitRegister() {
    alert("on click");

    const data = {
        name: document.getElementById('name').value,
        contact: document.getElementById('contact').value,
        password: document.getElementById('password').value
    };

    console.log(data); // Debugging purpose
    // alert(JSON.stringify(data)); // Optional

    fetch('/registered', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => Promise.reject(err));
        }
        return response.json();
    })
    .then(result => {
        alert(result.message || 'Registration successful!');
        window.location.href = '/'; // Redirect to home
    })
    .catch(error => {
        // This assumes `message` is a visible div
        const errorBox = document.getElementById('message');
        if (errorBox) {
            errorBox.innerText = error.message || 'An error occurred';
        } else {
            alert(error.message || 'An error occurred');
        }
    });
}


function loadRegisterForm(event) {
    event.preventDefault(); // prevent page reload

    fetch('/register.html') // path to your register.html in /static/
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to load register form.");
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('form-container').innerHTML = html;
        })
        .catch(error => {
            console.error("Error loading register form:", error);
            alert("Could not load the registration form.");
        });
}
