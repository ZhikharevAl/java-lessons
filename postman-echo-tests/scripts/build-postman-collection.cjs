const fs = require('node:fs');
const path = require('node:path');

const project = path.resolve(__dirname, '..');
const readJson = relative => JSON.parse(fs.readFileSync(path.join(project, relative), 'utf8').replace(/^\uFEFF/, ''));
const original = readJson('collections/Postman Echo.postman_collection.json');
const folder = original.item.find(item => item.name === 'Request Methods');
const fixtures = {
    'GET Request': 'get',
    'POST Raw Text': 'post-raw-text',
    'POST Form Data': 'post-form-data',
    'PUT Request': 'put',
    'PATCH Request': 'patch',
    'DELETE Request': 'delete'
};

if (!folder || folder.item.length !== Object.keys(fixtures).length) {
    throw new Error('Изменился состав папки Request Methods: проверьте покрытие тестами.');
}

for (const item of folder.item) {
    const fixtureName = fixtures[item.name];
    if (!fixtureName) throw new Error(`Нет ожидаемого ответа для ${item.name}`);
    const expected = readJson(`src/test/resources/expected/${fixtureName}.json`);
    item.request.header = [
        { key: 'Accept', value: expected.headers.accept },
        { key: 'User-Agent', value: expected.headers['user-agent'] },
        { key: 'Accept-Encoding', value: 'identity' }
    ];
    if (expected.headers['content-type']) {
        item.request.header.push({ key: 'Content-Type', value: expected.headers['content-type'] });
    }
    item.protocolProfileBehavior = {
        disableBodyPruning: true,
        disableCookies: true,
        disabledSystemHeaders: { 'postman-token': true, 'cache-control': true }
    };
    item.response = [];
    item.event = [{
        listen: 'test',
        script: {
            type: 'text/javascript',
            exec: [
                'pm.test("Status code is 200", function () {',
                '    pm.response.to.have.status(200);',
                '});',
                '',
                `const expected = ${JSON.stringify(expected, null, 4)};`,
                'pm.test("All response body fields match", function () {',
                '    pm.expect(pm.response.json()).to.deep.equal(expected);',
                '});'
            ].join('\n').split('\n')
        }
    }];
}

const collection = {
    info: {
        name: 'Postman Echo — lessons-16',
        description: 'Request Methods: код ответа 200 и строгое сравнение всех полей JSON. Источник запросов: официальная коллекция Postman Echo. Ожидаемые ответы общие с Java-тестами.',
        schema: 'https://schema.getpostman.com/json/collection/v2.1.0/collection.json'
    },
    item: [folder]
};
const destination = path.join(project, 'collections/Request Methods.tests.postman_collection.json');
fs.writeFileSync(destination, JSON.stringify(collection, null, 2) + '\n');
console.log(`Сохранено ${folder.item.length} запросов с проверками: ${destination}`);
