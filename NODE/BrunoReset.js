var http = require("http");

var post_data = JSON.stringify(
    {
        "data": {
            "title": "ClearMuralDB",
        },
        "registration_ids": ["APA91bH3D6VbNV4D1az4b23X3Ck7alHl1voS2jNIndZoeZb7ogWkaix0QmIZOpCGwDsC4H36EOK1w3ggxw72E0F6xvKGtrYD1YLShDzrV5zZLCvC1BYnFeCN1iTCwsfBJaKZ04sxkjrD"]
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
