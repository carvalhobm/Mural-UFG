var http = require("http");

var post_data = JSON.stringify(
    {
        "data": {
            "title": "Nao havera aula dia 3 de Julho",
			"news": "Nao havera aula Sexta-Feira, dia 3 de Julho, pois...",
			"author": "Marcelo Quinta",
			"authorbelongs": 26,
			"datetime": "1435088900000",
            "relevance" : 0,
			"url": "https://dl.dropboxusercontent.com/s/hqqp0sfgfomcvlr/02.json"
        },
		"registration_ids": ["APA91bGW1P0fD_me6pYKOuU5S5YWn4qFUs-ebg15If1vBPvRI_qHzKkd4HEuak1ifOaQHc1SCAiIDOJG_1sEAwHSt7cfx6GAstdMuOTFP6ZJ-WThfXtn6yvM9A67npUz-7XtVj0-A45Z"]
    }
);

var options = {
    hostname: "android.googleapis.com",
    port: 80,
    path: "/gcm/send",
    method: "POST",
    headers: {
        "content-type": "application/json",
        "content-length": post_data.length,
        "authorization": "key=AIzaSyAhqrHM94r1m6q-IiAYz6mViXpNTuH6QjA"
    }
};

var req = http.request(options, function(res) {
    res.setEncoding("utf8");
    res.on("data", function(chunk) {
        console.log("BODY: " + chunk);
    });
	
	console.log("STATUS: " + res.statusCode);
    console.log("HEADERS: " + JSON.stringify(res.headers));
});

req.on("error", function(e) {
    console.log("problem with request: " + e.message);
    console.log(e.stack);
});

// write data to request body
req.write(post_data);
req.end();
