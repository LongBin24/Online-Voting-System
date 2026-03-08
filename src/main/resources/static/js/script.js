let selectedCandidateId = null;

function confirmVote(id, name) {
    selectedCandidateId = id;
    document.getElementById('confirmText').innerText = `តើអ្នកប្រាកដទេថានឹងបោះឆ្នោតឱ្យបេក្ខជន៖ ${name}?`;
    document.getElementById('confirmModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('confirmModal').style.display = 'none';
}

async function submitVote() {
    const btn = document.getElementById('finalVoteBtn');
    btn.disabled = true;
    btn.innerText = "កំពុងរក្សាទុក...";

    try {
        const response = await fetch('/api/votes/cast', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                candidateId: selectedCandidateId
            })
        });

        const data = await response.json();

        if (response.ok && data.status === "success") {
            showSuccess(data.receiptToken);
        } else {

            alert("កំហុស៖ " + data.message);
            closeModal();
        }
    } catch (error) {
        console.error("Error:", error);
        alert("មិនអាចតភ្ជាប់ទៅកាន់ Server បានទេ!");
    } finally {
        btn.disabled = false;
        btn.innerText = "យល់ព្រមបោះឆ្នោត";
    }
}

function showSuccess(token) {
    document.getElementById('confirmModal').style.display = 'none';
    document.getElementById('candidate-list').style.display = 'none';
    document.querySelector('h1').style.display = 'none';

    document.getElementById('successArea').style.display = 'block';
    document.getElementById('receiptToken').innerText = token;
}

document.getElementById('finalVoteBtn').addEventListener('click', submitVote);

const candidates = [
    { id: 1, name: "លោក សុខ ជា", party: "គណបក្ស ក" },
    { id: 2, name: "កញ្ញា ចាន់ ធី", party: "គណបក្ស ខ" }
];

const listDiv = document.getElementById('candidate-list');
candidates.forEach(c => {
    listDiv.innerHTML += `
        <div class="card">
            <h3>${c.name}</h3>
            <p>${c.party}</p>
            <button onclick="confirmVote(${c.id}, '${c.name}')" class="btn-vote">បោះឆ្នោត</button>
        </div>
    `;
});