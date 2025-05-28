function submitRegister() {

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
        const messageBox = document.getElementById('message');
        if (messageBox) {
            messageBox.innerText = 'Registration successful!';
            messageBox.style.color = 'black'; // ✅ Success message in black
        }
        // Redirect after a delay (optional)
        setTimeout(() => {
            window.location.href = '/';
        }, 1500);
    })
    .catch(error => {
        const errorBox = document.getElementById('message');
        if (errorBox) {
            errorBox.innerText = error.message || 'An error occurred';
            errorBox.style.color = 'red';
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

