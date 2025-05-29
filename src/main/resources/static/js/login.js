 function sendLoginCode() {
            const email = document.getElementById('customer_email').value;
            const messageBox = document.getElementById('responseMessage');

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if (!emailRegex.test(email)) {
                messageBox.textContent = 'Please enter a valid email address.';
                messageBox.style.color = 'red';
                return;
            }

            fetch('/send-login-code', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email })
            })
            .then(res => res.json())
            .then(data => {
                messageBox.textContent = data.message;
                messageBox.style.color = 'green';
                document.getElementById('emailSection').style.display = 'none';
                document.getElementById('sbmit-btn').style.display = 'none';

                document.getElementById('codeSection').style.display = 'block';
            })
            .catch(err => {
                messageBox.textContent = 'Failed to send login code.';
                messageBox.style.color = 'red';
            });
        }

        function verifyCode() {
            const email = document.getElementById('customer_email').value;
            const code = document.getElementById('code').value;
            const messageBox = document.getElementById('responseMessage');

            fetch('/verify-code', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, code })
            })
            .then(res => {
                if (!res.ok) {
                    throw new Error("Invalid code");
                }
                return res.json();
            })
            .then(data => {
                messageBox.textContent = data.message;
                messageBox.style.color = 'green';
                // Optionally redirect:
                 window.location.href = "/";
            })
            .catch(err => {
                messageBox.textContent = 'Invalid or expired code.';
                messageBox.style.color = 'red';
            });
        }