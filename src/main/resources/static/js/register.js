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

function showRegistrationPage(){
event.preventDefault();
        document.getElementById('sidebar-content-login').style.display = 'none';
        document.getElementById('register-form').style.display = 'block';

}
function showLogin() {
    event.preventDefault();
    document.getElementById('register-form').style.display = 'none';
    document.getElementById('sidebar-content-login').style.display = 'block';
}

function loadRegisterForm(event) {

    fetch('/register')  // Adjust path as needed
        .then(response => {
            if (!response.ok) {
                throw new Error("Could not load register form");
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('sidebar-content-register').innerHTML = html;
            showSidebar(); // Ensure sidebar is visible
        })
        .catch(error => {
            console.error("Error loading form:", error);
            alert("Failed to load registration form.");
        });
}

function showSidebar() {
    const sidebar = document.getElementById('sidebar');
    if (sidebar) {
        sidebar.style.display = 'block'; // or add 'open' class
    }
}

