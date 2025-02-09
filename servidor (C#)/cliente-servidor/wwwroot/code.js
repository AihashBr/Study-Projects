function encontrarEan(object, ean13) {
    for (const key in object) {
        if (object[key].ean == ean13) {
            return object[key]
        }
    }
}
window.appFunction = {
    dataBase: {},
    cancelar: function () {
        document.getElementById('alert1').style.display = 'none'
    },
    telaLogin: function () {
        document.getElementById('tela').innerHTML = `
        <div class="backgroundTop">
            <div style="background-color: #0000008f; display: flex; align-items: center; justify-content: center; width: 100%; height: 100%;">
                <div class="conteiner1">
                    <input class="input" type="text" placeholder="User" id="user">
                    <input class="input" style="margin-top: 10px;" type="password" placeholder="Senha" id="senha">
                    <button class="btn1" style="margin-top: 20px; width: 100%;" onclick="appFunction.entra()">Entra</button>
                </div>
            </div>
        </div>
        `
    },
    entra: function () {
        if (localStorage.getItem('app') !== null) {
            let app = JSON.parse(localStorage.getItem('app'))
            if (document.getElementById('senha').value == app.user[document.getElementById('user').value]) {
                appFunction.telaMenu()
            }
        } else {
            let app = {
                user: {},
                estoque: {}
            }
            app.user[document.getElementById('user').value] = document.getElementById('senha').value
            localStorage.setItem('app', JSON.stringify(app))
        }
    },
    telaMenu: function () {
        document.getElementById('tela').innerHTML = `
        <div style="width: calc(100vw - 20px); padding: 10px; padding-top: 60px;">
            <div style="display: flex; flex-direction: row;">
                <button class="btn1" style="margin-top: 5px; width: calc(50vw - 12.5px);" onclick="appFunction.entradaOpen()">Entrada</button>
                <button class="btn1" style="margin-top: 5px; width: calc(50vw - 12.5px); margin-left: 5px" onclick="appFunction.saidaOpen()">Saída</button>
            </div>
            <button class="btn1" style="margin-top: 5px; width: 100%;" onclick="appFunction.registrarOpen()">Registrar</button>
            <button class="btn1" style="margin-top: 5px; width: 100%;" onclick="appFunction.pesquisarOpen()">Pesquisar</button>
        </div>
        <div style="width: calc(100vw - 10px); height: calc(100vh - 190px); padding-left: 10px; overflow-y: auto;" id="div-estoque">
        </div>
        `
        appFunction.gerarList()
    },
    gerarList: async function () {
        document.getElementById('div-estoque').innerHTML = ''
        let response = await fetch('http://localhost:5112/api/home/readDB')
        let data = await response.json()
        appFunction.dataBase = data.dbBase
        for (let key in data.dbBase) {
            document.getElementById('div-estoque').innerHTML += `<button class="btn2" style="margin-top: 5px; width: calc(100vw - 20px); text-align: start;" onclick="appFunction.editarOpen('${data.dbBase[key].ean}')">${data.dbBase[key].ean} | ${data.dbBase[key].nome} - Quantidade ${data.dbBase[key].quantidade} - valor ${data.dbBase[key].valor}</button>`
        }
    },    
    entradaOpen: function () {
        document.getElementById('alert2').innerHTML = `
        <input class="input" type="text" placeholder="Código" id="ean13">
        <input class="input" type="text" style="margin-top: 10px;" placeholder="Quantidade" id="quantidade">
        <div style="display: flex; flex-direction: row;">
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px);" onclick="appFunction.cancelar()">Cancelar</button>
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px); margin-left: 5px" onclick="appFunction.entrada()">Entrada</button>
        </div>
        `
        document.getElementById('alert1').style.display = 'flex'
    },
    entrada: async function () {
        let dataBase = encontrarEan(appFunction.dataBase , document.getElementById('ean13').value)
        let quantidade = parseInt(document.getElementById('quantidade').value) + parseInt(dataBase.quantidade)
        let conteudo = {
            id: parseInt(dataBase.id),
            nome: dataBase.nome,
            ean: parseInt(dataBase.ean),
            quantidade: parseInt(quantidade)
        }
        const response = await fetch('http://localhost:5112/api/home/updateDB', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(conteudo)
        })
        if (response.ok) {
            appFunction.cancelar()
            appFunction.telaMenu()
        }
    },
    saidaOpen: function () {
        document.getElementById('alert2').innerHTML = `
        <input class="input" type="text" placeholder="Código" id="ean13">
        <input class="input" type="text" style="margin-top: 10px;" placeholder="Quantidade" id="quantidade">
        <div style="display: flex; flex-direction: row;">
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px);" onclick="appFunction.cancelar()">Cancelar</button>
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px); margin-left: 5px" onclick="appFunction.saida()">Entrada</button>
        </div>
        `
        document.getElementById('alert1').style.display = 'flex'
    },
    saida: async function () {
        let dataBase = encontrarEan(appFunction.dataBase , document.getElementById('ean13').value)
        let quantidade = parseInt(dataBase.quantidade) - parseInt(document.getElementById('quantidade').value)
        let conteudo = {
            id: parseInt(dataBase.id),
            nome: dataBase.nome,
            ean: parseInt(dataBase.ean),
            quantidade: parseInt(quantidade)
        }
        const response = await fetch('http://localhost:5112/api/home/updateDB', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(conteudo)
        })
        if (response.ok) {
            appFunction.cancelar()
            appFunction.telaMenu()
        }
    },
    registrarOpen: function () {
        document.getElementById('alert2').innerHTML = `
        <input class="input" type="text" placeholder="Nome" id="nome">
        <input class="input" style="margin-top: 10px;" type="text" placeholder="Código" id="ean13">
        <input class="input" style="margin-top: 10px;" type="text" placeholder="Quantidade" id="quantidade">
        <div style="display: flex; flex-direction: row;">
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px);" onclick="appFunction.cancelar()">Cancelar</button>
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px); margin-left: 5px" onclick="appFunction.registrar()">Entrada</button>
        </div>
        `
        document.getElementById('alert1').style.display = 'flex'
    },
    registrar: async function () {
        let conteudo = {
            nome: document.getElementById('nome').value,
            ean: parseInt(document.getElementById('ean13').value),
            quantidade: parseInt(document.getElementById('quantidade').value)
        }
        const response = await fetch('http://localhost:5112/api/home/createDB', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(conteudo)
        })
        if (response.ok) {
            appFunction.cancelar();
            appFunction.telaMenu();
        }
    },    
    pesquisarOpen: function () {
        document.getElementById('alert2').innerHTML = `
        <input class="input" type="text" placeholder="Código" id="ean13">
        <div style="width: 100%;" id="div-pesquisar"></div>
        <div style="display: flex; flex-direction: row;">
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px);" onclick="appFunction.cancelar()">Fechar</button>
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px); margin-left: 5px" onclick="appFunction.pesquisar()">Entrada</button>
        </div>
        `
        document.getElementById('alert1').style.display = 'flex'
    },
    pesquisar: function () {
        let dataBase = encontrarEan(appFunction.dataBase , document.getElementById('ean13').value)
        document.getElementById('div-pesquisar').innerHTML = `
        <button class="btn2" style="margin-top: 5px; width: calc(100vw - 58px); text-align: start;" onclick="appFunction.entra()">${dataBase.ean} | ${dataBase.nome} - Qantidade ${dataBase.quantidade}</button>
        `
    },
    editarOpen: function (ean13) {
        let dataBase = encontrarEan(appFunction.dataBase , ean13)
        document.getElementById('alert2').innerHTML = `
        <input class="input" type="text" placeholder="Nome" id="nome">
        <input class="input" style="margin-top: 10px;" type="text" placeholder="Quantidade" id="quantidade">
        <input class="input" style="margin-top: 10px;" type="text" placeholder="Valor" id="valor">
        <button class="btn1" style="margin-top: 10px; width: calc(100vw - 58px);" onclick="appFunction.deletar(${ean13}, ${dataBase.id})">Deletar</button>
        <div style="display: flex; flex-direction: row;">
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px);" onclick="appFunction.cancelar()">Fechar</button>
            <button class="btn1" style="margin-top: 20px; width: calc(50% - 2.5px); margin-left: 5px" onclick="appFunction.editar(${ean13}, ${dataBase.id})">Editar</button>
        </div>
        `
        document.getElementById('alert1').style.display = 'flex'
        document.getElementById('nome').value = dataBase.nome
        document.getElementById('quantidade').value = dataBase.quantidade
        document.getElementById('valor').value = dataBase.valor
    },
    editar: async function (ean13, id) {
        let conteudo = {
            id: parseInt(id),
            nome: document.getElementById('nome').value,
            ean: parseInt(ean13),
            quantidade: parseInt(document.getElementById('quantidade').value),
            valor: parseInt(document.getElementById('valor').value)
        }
        const response = await fetch('http://localhost:5112/api/home/updateDB', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(conteudo)
        })
        if (response.ok) {
            appFunction.cancelar()
            appFunction.telaMenu()
        }
    },
    deletar: async function (ean13, id) {
        let conteudo = {
            id: parseInt(id),
            nome: 'delete',
            ean: ean13,
            quantidade: parseInt(document.getElementById('quantidade').value)
        }
        const response = await fetch('http://localhost:5112/api/home/deleteDB', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(conteudo)
        })
        if (response.ok) {
            appFunction.cancelar()
            appFunction.telaMenu()
        }
    }
}
appFunction.telaMenu()