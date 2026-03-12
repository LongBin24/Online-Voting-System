window.confirmVote = function(id, name) {
    window.selectedCandidateId = id;
    document.getElementById('confirmText').innerText = `Are you sure you want to vote for: ${name}?`;
    document.getElementById('confirmModal').style.display = 'block';
};

window.closeModal = function() {
    document.getElementById('confirmModal').style.display = 'none';
};

async function loadCandidates() {
    try {
        const response = await fetch('/api/votes/candidates');

        if (!response.ok) {
            throw new Error("Failed to load candidates (Status: " + response.status + ")");
        }

        const candidates = await response.json();
        const listDiv = document.getElementById('candidate-list');

        if (listDiv) {
            listDiv.innerHTML = '';

            candidates.forEach(c => {
                listDiv.innerHTML += `
                    <div class="card">
                        <h3>${c.name}</h3>
                        <p>${c.party}</p>
                        <button onclick="confirmVote(${c.id}, '${c.name}')" class="btn-vote">Vote Now</button>
                    </div>`;
            });
        }
    } catch (error) {
        console.error("Error loading candidates:", error);
    }
}

async function submitVote() {
    const btn = document.getElementById('finalVoteBtn');
    btn.disabled = true;
    btn.innerText = "Saving...";

    try {
        const response = await fetch('/api/votes/cast', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ candidateId: window.selectedCandidateId })
        });

        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            const data = await response.json();
            if (response.ok && data.status === "success") {
                showSuccess(data.receiptToken);
            } else {
                alert("Error: " + data.message);
                closeModal();
            }
        } else {
            alert("Please login first!");
            window.location.href = "/login";
        }
    } catch (error) {
        alert("Server connection failed!");
    } finally {
        btn.disabled = false;
        btn.innerText = "Confirm Vote";
    }
}

function showSuccess(token) {
    document.getElementById('confirmModal').style.display = 'none';
    document.getElementById('candidate-list').style.display = 'none';
    document.querySelector('h1').style.display = 'none';
    document.getElementById('successArea').style.display = 'block';
    document.getElementById('receiptToken').innerText = token;
}

document.addEventListener('DOMContentLoaded', () => {
    loadCandidates();
    document.getElementById('finalVoteBtn').addEventListener('click', submitVote);
});