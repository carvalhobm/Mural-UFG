var http = require("http");

var post_data = JSON.stringify(
    {
        "data": {
            "title": "ClearMuralDB",
        },
        "registration_ids": ["APA91bFikmWj-xpcny7ZsHgKbkFVEodfphYigqjEcMR65ajNNlh_YohwacdEbDxJ1a0ANz3peLF014v9q-CZnoOaEcbBa-Z4yzd-I2OjzFh0RBog2fPA4mrhtPQXdIybjG7I4PTrtgaq"]
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
