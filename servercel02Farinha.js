var http = require("http");

var post_data = JSON.stringify(
    {
        "data": {
            "title": "Nao havera aula dia 3 de Julho",
			"news": "Nao havera aula Sexta-Feira, dia 3 de Julho, pois estarei viajando.",
			"photo": "",
			"author": "Marcelo Quinta",
			"authorbelongs": "INF",
			"datetime": "1435088900000",
            "relevance" : 0
        },
        "registration_ids": ["APA91bHTfmqxQ5rbY90r8VQMR7ZvhzZwAmAudCSpJrgSxAxNqnJQ9fpxUj87TzSmf9au808Tjz9nqKO73bHqgu4_FoB4HARjT0d4LObOAlWcAbxXNikQgbqJ2DuUTyDFHFXk58k1emdm"]
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
