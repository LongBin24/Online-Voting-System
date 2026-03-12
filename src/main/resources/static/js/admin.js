document.addEventListener('DOMContentLoaded', () => {
    loadCandidates();
    loadVotingResults();
});

async function loadCandidates() {
    try {
        const response = await fetch('/admin/candidates/all');
        const candidates = await response.json();
        const tbody = document.getElementById('candidateTableBody');
        if (!tbody) return;

        tbody.innerHTML = '';
        candidates.forEach(c => {
            tbody.innerHTML += `
                <tr>
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td>${c.party}</td>
                    <td><button onclick="deleteCandidate(${c.id})" class="btn-delete">លុប</button></td>
                </tr>`;
        });
    } catch (error) {
        console.error("Error loading candidates:", error);
    }
}

async function loadVotingResults() {
    try {
        const response = await fetch('/api/dashboard/results');
        if (!response.ok) {
            throw new Error("រកមិនឃើញ API លទ្ធផល (404)");
        }
        const results = await response.json();
        const tbody = document.getElementById('votingResultsBody');
        if (!tbody) return;

        tbody.innerHTML = '';

        const totalVotes = results.reduce((sum, item) => sum + item.voteCount, 0);

        results.forEach(res => {
            const percentage = totalVotes > 0 ? ((res.voteCount / totalVotes) * 100).toFixed(1) : 0;
            tbody.innerHTML += `
                <tr>
                    <td><strong>${res.candidateName}</strong> (${res.party})</td>
                    <td>${res.voteCount} សម្លេង</td>
                    <td>
                        <div class="progress-container" style="background: #eee; width: 100px; height: 10px; display: inline-block; border-radius: 5px; margin-right: 10px;">
                            <div class="progress-bar" style="width: ${percentage}%; background: #007bff; height: 100%; border-radius: 5px;"></div>
                        </div>
                        ${percentage}%
                    </td>
                </tr>`;
        });
    } catch (error) {
        console.error("Error loading results:", error);
    }
}

async function deleteCandidate(id) {
    if (confirm("តើអ្នកពិតជាចង់លុបបេក្ខជននេះមែនទេ?")) {
        try {
            const response = await fetch(`/admin/candidates/delete/${id}`, { method: 'DELETE' });
            if (response.ok) {
                alert("លុបជោគជ័យ!");
                location.reload();
            }
        } catch (error) {
            alert("មិនអាចលុបបានទេ!");
        }
    }
}

async function saveCandidate() {
    const name = document.getElementById('candName').value;
    const party = document.getElementById('candParty').value;
    const btn = document.querySelector('.btn-save');

    if (!name || !party) {
        alert("Please fill in both Candidate Name and Party!");
        return;
    }

    try {
        btn.disabled = true;
        const response = await fetch('/admin/candidates/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, party })
        });

        if (response.ok) {
            alert("Candidate added successfully!");
            location.reload();
        } else {
            alert("Failed to save candidate!");
        }
    } catch (error) {
        alert("Server connection failed!");
    } finally {
        btn.disabled = false;
    }
}