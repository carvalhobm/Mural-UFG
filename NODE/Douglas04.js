var http = require("http");

var post_data = JSON.stringify(
    {
        "data": {
            "title": "Lorem ipsum dolor sit amet",
			"news": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tempus...",
			"author": "Reitoria",
			"authorbelongs": 0,
			"datetime": "1435264970000",
            "relevance" : 0,
			"url": "https://dl.dropboxusercontent.com/s/958xskzyi2x4mjc/04.json?dl=0"
        },
		"registration_ids": ["APA91bGI2AuNcMPoUcPxsB64OHd76juXtna2eZWXzcFMjjtAeso_C5Naj9_fv4vECkgeEJWxNdaIxQq_vn1aC58E6BjjEPdZ1mIvfOi7LxGkBBBF2kfCx5tl8s2kKmm8b7g-2vwMDrri"]
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
