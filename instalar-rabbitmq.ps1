# Script para instalar RabbitMQ no Windows
# Execute como Administrador

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "   Instalador RabbitMQ - AutoTTU    " -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se Chocolatey está instalado
if (Get-Command choco -ErrorAction SilentlyContinue) {
    Write-Host "✅ Chocolatey encontrado!" -ForegroundColor Green
    Write-Host "📦 Instalando RabbitMQ..." -ForegroundColor Yellow
    
    choco install rabbitmq -y
    
    Write-Host "✅ RabbitMQ instalado com sucesso!" -ForegroundColor Green
    Write-Host ""
    Write-Host "🚀 Iniciando RabbitMQ..." -ForegroundColor Yellow
    
    # Iniciar serviço
    Start-Service RabbitMQ
    
    # Habilitar management plugin
    & "C:\Program Files\RabbitMQ Server\rabbitmq_server-*\sbin\rabbitmq-plugins.bat" enable rabbitmq_management
    
    Write-Host ""
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host "✅ INSTALAÇÃO CONCLUÍDA!" -ForegroundColor Green
    Write-Host "=====================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "🌐 Acesse o painel: http://localhost:15672" -ForegroundColor Cyan
    Write-Host "👤 Usuário: guest" -ForegroundColor Cyan
    Write-Host "🔑 Senha: guest" -ForegroundColor Cyan
    Write-Host ""
    
} else {
    Write-Host "❌ Chocolatey não encontrado!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Escolha uma opção:" -ForegroundColor Yellow
    Write-Host "1. Instalar Chocolatey e depois RabbitMQ (Recomendado)" -ForegroundColor White
    Write-Host "2. Baixar instalador manual" -ForegroundColor White
    Write-Host "3. Usar CloudAMQP (Grátis, na nuvem)" -ForegroundColor White
    Write-Host ""
    
    $opcao = Read-Host "Digite o número da opção"
    
    switch ($opcao) {
        "1" {
            Write-Host ""
            Write-Host "📦 Instalando Chocolatey..." -ForegroundColor Yellow
            Set-ExecutionPolicy Bypass -Scope Process -Force
            [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
            Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
            
            Write-Host "✅ Chocolatey instalado!" -ForegroundColor Green
            Write-Host "⚠️ Por favor, REABRA o PowerShell como Administrador e execute este script novamente." -ForegroundColor Yellow
        }
        "2" {
            Write-Host ""
            Write-Host "📥 Abrindo páginas de download..." -ForegroundColor Yellow
            Start-Process "https://www.erlang.org/downloads"
            Start-Process "https://www.rabbitmq.com/download.html"
            Write-Host ""
            Write-Host "➡️ Instale primeiro o Erlang, depois o RabbitMQ" -ForegroundColor Cyan
        }
        "3" {
            Write-Host ""
            Write-Host "☁️ Abrindo CloudAMQP..." -ForegroundColor Yellow
            Start-Process "https://www.cloudamqp.com/"
            Write-Host ""
            Write-Host "Após criar sua instância, atualize o application.properties com:" -ForegroundColor Cyan
            Write-Host "spring.rabbitmq.addresses=amqps://seu-usuario:senha@servidor.rmq.cloudamqp.com/usuario" -ForegroundColor Yellow
        }
        default {
            Write-Host "❌ Opção inválida!" -ForegroundColor Red
        }
    }
}

Write-Host ""
Write-Host "Pressione qualquer tecla para sair..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

