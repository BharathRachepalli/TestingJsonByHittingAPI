let popBtn = document.getElementById('popBtn');
popBtn.addEventListener('click', popHandler);

function popHandler() {
  console.log('You have clicked the pop handler');

  // Instantiate an xhr object
  let xhr = new XMLHttpRequest();

  // Open the object
  xhr.open('GET', 'https://restcountries.eu/rest/v2/name/united', true);

  // What to do when response is ready
  xhr.onload = function () {
    if (this.status === 200) {
      let obj = JSON.parse(this.responseText);
      console.log(obj);
      
      let list = document.getElementById('list');
      str = '';
      for (key in obj) {
        str += `<li>${obj[key].name} </li>`;
      }
      list.innerHTML = str;
      
      $.ajax({
    	     url: 'senddata',
    	     type: 'POST', 
    	     dataType: 'json',
    	     data: JSON.stringify(obj),
    	     success: function(result) {
    	       alert('SUCCESS');
    	     }
    	});
      
    } else {
      console.log('Some error occured');
    }
  };

  // send the request
  
  xhr.send();
}