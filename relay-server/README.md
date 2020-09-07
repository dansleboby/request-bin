## Request Bin Relay

This small utility allows the "reverse proxying" of a Request Bin stream to a target of your choice.

The event stream will be relayed one-to-one; no modifications will be applied to the body, http headers, and query parameters.

### Install

This package has not yet been published to any public registry. Please install from source, e.g.:

```bash
git clone https://github.com/MrGraversen/request-bin.git;
cd request-bin/relay-server;
npm install;
npm run build;
npm install -g .;
```

That should be it. You are now able to invoke the CLI by running `requestbin` in your favorite terminal.

### Usage

The CLI takes three parameters:

* `host`: If you are running Request Bin yourself, you are able to override the target host (optional, defaults to `bin.graversen.io`)
* `stream`: The ID of the stream you are targeting (required)
* `target`: That's your target URL! (required)

**Example**

```bash
requestbin --stream 46e39d85-eb3d-4a3a-928e-ce62f7daad61 --target http://localhost:8080/my/endpoint
```