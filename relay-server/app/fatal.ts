const chalk = require('chalk');

export const fatal = (message: string) => {
    console.log(chalk.red.bold('Error: ') + message);
    process.exit(1);
};
