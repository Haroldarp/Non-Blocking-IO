const baseUrl = 'http://localhost:8080/images/async/';

function requestImagePromise(method, url){
    return new Promise(function (resolve, reject) {

        let xhr = new XMLHttpRequest();
        xhr.open(method, url);

        xhr.onload = function () {
            if (this.status == 200) {
                var response = xhr.responseText;
                var binary = ""
                
                for(i=0;i<response.length;i++){
                    binary += String.fromCharCode(response.charCodeAt(i) & 0xff);
                }

                resolve('data:image/jpeg;base64,' + btoa(binary));

            } else {
                reject({
                    status: this.status,
                    statusText: xhr.statusText
                });
            }
        };

        xhr.onerror = function () {
            reject({
                status: this.status,
                statusText: xhr.statusText
            });
        };

        xhr.overrideMimeType('text/plain; charset=x-user-defined');
        xhr.send();
    });
}

async function blockingRequest(){
    const startTime = Date.now();
    const baseId = 'image-'

    for (let index = 1; index <= 25; index++) {
        try {
            var result = await requestImagePromise("GET", `${baseUrl}${baseId}${index}`);
            document.getElementById(`${baseId}${index}`).src = result;
        } catch (e) {
            console.error(e);
        }
        
    }

    const endTime = Date.now();
    const duration = (endTime - startTime) / 1000;

    console.log(`Duracion: ${duration} segundos`);
    alert(`Duracion: ${duration} segundos`);

}

function nonBlockingRequest(){
    const startTime = Date.now();
    const baseId = 'image-'
    var counter = 0;

    for (let index = 1; index <= 25; index++) {
        requestImagePromise("GET", `${baseUrl}${baseId}${index}`).then((result)=>{
            document.getElementById(`${baseId}${25+index}`).src = result;
            
            counter++;
            if(counter == 25){
                const endTime = Date.now();
                const duration = (endTime - startTime) / 1000;
                console.log(`Duracion: ${duration} segundos`);
                alert(`Duracion: ${duration} segundos`);
            }
        });
        
    }
}