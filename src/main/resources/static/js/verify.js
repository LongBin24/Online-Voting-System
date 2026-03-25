async function checkVote() {
    const token = document.getElementById('tokenInput').value;
    const resArea = document.getElementById('resultArea');

    try {
        const response = await fetch(`/api/verify/${token}`);
        const data = await response.json();

        resArea.style.display = 'block';
        document.getElementById('resMessage').innerText = data.message;

        if (response.ok) {
            document.getElementById('resHash').innerText = data.voteHash;
            document.getElementById('resPrevHash').innerText = data.previousHash;
            document.getElementById('resTime').innerText = data.timestamp;
            resArea.className = "result-box success";
        } else {
            document.getElementById('resHash').innerText = "-";
            resArea.className = "result-box error";
        }
    } catch (error) {
        alert("មានបញ្ហាក្នុងការតភ្ជាប់!");
    }
}