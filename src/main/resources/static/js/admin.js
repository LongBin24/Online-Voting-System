document.addEventListener('DOMContentLoaded', () => {
    loadCandidates();
    loadVotingResults();
});

async function loadCandidates() {
    const response = await fetch('/admin/candidates/all');
    const candidates = await response.json();
    const tbody = document.getElementById('candidateTableBody');
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
}

async function loadVotingResults() {
    const response = await fetch('/api/dashboard/results');
    const tbody = document.getElementById('votingResultsBody');
    tbody.innerHTML = '';

    const totalVotes = results.reduce((sum, item) => sum + item.voteCount, 0);

    results.forEach(res => {
        const percentage = totalVotes > 0 ? ((res.voteCount / totalVotes) * 100).toFixed(1) : 0;
        tbody.innerHTML += `
            <tr>
                <td><strong>${res.candidateName}</strong> (${res.party})</td>
                <td>${res.voteCount} សម្លេង</td>
                <td>
                    <div class="progress-bar" style="width: ${percentage}%"></div>
                    ${percentage}%
                </td>
            </tr>`;
    });
}