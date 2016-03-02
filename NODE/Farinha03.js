var http = require("http");

var post_data = JSON.stringify(
    {
        "data": {
            "title": "Tiroteio no Reuni",
			"news": "Acaba de acontecer um tiroteio no Reuni, proximo ao Centro...",
			"photo": "",
			"author": "Reitoria",
			"authorbelongs": 0,
			"datetime": "1435088900000",
            "relevance" : 1,
			"url": "https://dl.dropboxusercontent.com/s/xfhuqb1av2l3rbp/03.json?dl=0"
        },
		"registration_ids": ["APA91bFttGXNKDtDyZjOgg4qSc7AXFJPLOkDOjpOfbzboJQOXEEPWLYnoux7biW88UTX6OxgFCJiEnPxxTBgsZ66DSbyUcbrBvkIjMb6KfC8VEtu8op1zglxa2_QA8zx_jVyCbAZVYrD"]
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
