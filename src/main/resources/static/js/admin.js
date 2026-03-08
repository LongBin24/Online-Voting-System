async function saveCondidate(){
const name = document.getElementById('candName').value;
const party = document.getElementById('candParty').value;
const response = await fetch('/admin/candidates/add',{
    method:'POST',
    headers:{'content-Type':'application/json'},
    body: JSON.stringify({name,party})
});
if(response.ok){
    alert("Success");
    location.reload();
}
}