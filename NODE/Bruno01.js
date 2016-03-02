var http = require("http");

var post_data = JSON.stringify(
    {
        "data": {
            "title": "Manutencao da Rede Eletrica",
			"news": "No dia 27/06/2015 a CELG fara uma manutencao na rede...",
			"photo": "",
			"author": "Reitoria",
			"authorbelongs": 0,
			"datetime": "1435264970000",
            "relevance" : 0,
			"url": "https://dl.dropboxusercontent.com/s/plt99l1zd5ux7eu/01.json"
        },
        "registration_ids": ["APA91bFCWI7TgMMcc3qqeUfpASgb6Sd9iREjgeV9EzYpn_4AzIiQgFz4-MvZ6tMsmjh2Gpz09fI-aPTqfmzj26d4AW9oxS_G7Pl7J6oXOWYbCqGbSqTWix7bN3Cj9IScC6x0mY-3tpxg"]
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
