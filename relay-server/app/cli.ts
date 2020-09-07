#!/usr/bin/env node

import {requestBinStream} from "./requestBinStream";

const defaultHost: string = 'https://bin.graversen.io'

const error = (message: string) => {
    console.log(chalk.red.bold('Error: ') + message);
    process.exit(1);
};

const chalk = require('chalk');
const args = require('minimist')(process.argv.slice(2));

const host: string = args.host || defaultHost;
const stream: string = args.stream;
const target: string = args.target;

if (!host || host === '') {
    error('Please specify a valid \'--host\' argument');
}

if (!stream || stream === '') {
    error('Please specify a valid \'--stream\' argument');
}

if (!target || target === '') {
    error('Please specify a valid \'--target\' argument');
}

console.log('============================');
console.log('Welcome to ' + chalk.bold.green('Request Bin Relay'))
console.log('============================');
console.log(chalk.cyan('Host: ') + host);
console.log(chalk.cyan('Stream: ') + stream);
console.log(chalk.cyan('Target: ') + target);

requestBinStream(host, stream, target);