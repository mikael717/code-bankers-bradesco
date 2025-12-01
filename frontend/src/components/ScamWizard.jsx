import { useState } from 'react';
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';

const ScamWizard = ({ visible, onHide }) => {
    const [step, setStep] = useState('INITIAL');
    const [scamType, setScamType] = useState('');

    const reset = () => {
        setStep('INITIAL');
        setScamType('');
    };

    const handleClose = () => {
        reset();
        onHide();
    };

    const headerElement = (
        <div className="flex align-items-center justify-content-center w-full relative">
            <span className="text-xl font-bold text-900 m-0">Assistente de Verificação</span>
        </div>
    );

    const renderContent = () => {
        switch (step) {
            case 'INITIAL':
                return (
                    <div className="flex flex-column gap-3 pt-2">
                        <p className="text-lg text-center mb-4 text-700">Quem entrou em contato com você usando este número?</p>
                        <Button
                            label="Parente ou Amigo (Filho, Mãe, etc)"
                            icon="pi pi-user"
                            className="p-button-outlined p-button-lg"
                            onClick={() => setStep('FAMILY')}
                        />
                        <Button
                            label="Loja, Banco ou Empresa"
                            icon="pi pi-shopping-bag"
                            className="p-button-outlined p-button-lg"
                            onClick={() => setStep('STORE')}
                        />
                    </div>
                );

            case 'FAMILY':
                return (
                    <div className="flex flex-column gap-3">
                        <p className="text-lg font-bold text-center text-700">Cenário: Parente ou Amigo</p>
                        <p className="mb-3 text-center">A pessoa disse que <strong>mudou de número</strong> e logo em seguida pediu dinheiro ou pagamento de conta?</p>
                        <div className="flex gap-2">
                            <Button label="Sim, pediu dinheiro" severity="danger" className="flex-1" onClick={() => { setScamType('GOLPE DO NOVO NÚMERO'); setStep('RESULT_SCAM'); }} />
                            <Button label="Não, conversa normal" severity="warning" className="flex-1" onClick={() => setStep('RESULT_WARNING_FAMILY')} />
                        </div>
                        <Button label="Voltar" className="p-button-text mt-2" onClick={() => setStep('INITIAL')} />
                    </div>
                );

            case 'STORE':
                return (
                    <div className="flex flex-column gap-3">
                        <p className="text-lg font-bold text-center text-700">Cenário: Loja ou Banco</p>
                        <p className="mb-3 text-center">Eles enviaram um link estranho, pediram senha ou código SMS?</p>
                        <div className="flex gap-2">
                            <Button label="Sim, pediram dados" severity="danger" className="flex-1" onClick={() => { setScamType('PHISHING / ROUBO DE CONTA'); setStep('RESULT_SCAM'); }} />
                            <Button label="Não, parece normal" severity="warning" className="flex-1" onClick={() => setStep('RESULT_WARNING_STORE')} />
                        </div>
                        <Button label="Voltar" className="p-button-text mt-2" onClick={() => setStep('INITIAL')} />
                    </div>
                );

            case 'RESULT_SCAM':
                return (
                    <div className="text-center pt-2">
                        <i className="pi pi-exclamation-triangle text-5xl text-red-600 mb-3"></i>
                        <h3 className="text-red-600 font-bold mt-0">Alta chance de {scamType}</h3>
                        <p className="text-700 mb-4 line-height-3">
                            Golpistas usam fotos roubadas ou se passam por lojas oficiais.
                        </p>
                        <div className="bg-red-50 p-3 border-round text-left mb-3 border-1 border-red-200">
                            <strong>🚫 Pare imediatamente:</strong>
                            <ul className="m-0 pl-3 mt-2 text-sm text-red-900">
                                <li className="mb-1">Não faça PIX ou transferências.</li>
                                <li className="mb-1">Não clique em links enviados.</li>
                                <li>Bloqueie este número.</li>
                            </ul>
                        </div>
                        <Button label="Entendi, vou bloquear" severity="danger" onClick={handleClose} className="w-full"/>
                    </div>
                );

            case 'RESULT_WARNING_FAMILY':
                return (
                    <div className="text-center pt-2">
                        <i className="pi pi-eye text-5xl text-yellow-600 mb-3"></i>
                        <h3 className="text-yellow-700 font-bold mt-0">Continue Atento</h3>
                        <p className="text-700 mb-4">
                            O número parece real, mas contas de WhatsApp podem ser hackeadas.
                        </p>

                        <div className="bg-yellow-50 p-3 border-round text-left mb-3 border-1 border-yellow-200">
                            <strong>🛡️ Confirmações Obrigatórias:</strong>
                            <ul className="m-0 pl-3 mt-2 text-sm text-yellow-900">
                                <li className="mb-2">
                                    <strong>Faça uma Chamada de Vídeo:</strong> É a única forma de ter 100% de certeza que é a pessoa. Golpistas recusam vídeo dizendo que a "câmera quebrou" ou a "internet está ruim".
                                </li>
                                <li className="mb-2">
                                    <strong>Confira o PIX:</strong> Se pedir dinheiro, veja se o nome no PIX é da pessoa mesmo. Se for nome de estranho, é golpe.
                                </li>
                                <li>
                                    <strong>Áudio não prova nada:</strong> Hoje em dia Inteligência Artificial consegue imitar voz.
                                </li>
                            </ul>
                        </div>
                        <Button label="Concluir" severity="warning" onClick={handleClose} className="w-full" outlined />
                    </div>
                );

            case 'RESULT_WARNING_STORE':
                return (
                    <div className="text-center pt-2">
                        <i className="pi pi-shield text-5xl text-yellow-600 mb-3"></i>
                        <h3 className="text-yellow-700 font-bold mt-0">Atenção aos Detalhes</h3>
                        <p className="text-700 mb-4">
                            Mesmo sendo uma loja real, golpistas podem estar tentando induzir um pagamento falso.
                        </p>

                        <div className="bg-yellow-50 p-3 border-round text-left mb-3 border-1 border-yellow-200">
                            <strong>🛍️ Sinais de Perigo:</strong>
                            <ul className="m-0 pl-3 mt-2 text-sm text-yellow-900">
                                <li className="mb-2">
                                    <strong>"Taxa Extra" ou "Alfândega":</strong> Se pedirem um PIX por fora para liberar entrega, é golpe. Tudo deve ser pago na plataforma oficial.
                                </li>
                                <li className="mb-2">
                                    <strong>Nunca passe códigos SMS:</strong> Lojas nunca pedem o código de 6 dígitos do seu WhatsApp.
                                </li>
                                <li>
                                    <strong>Pagamento com desconto absurdo:</strong> Desconfie de ofertas "só agora no PIX" fora do site oficial.
                                </li>
                            </ul>
                        </div>
                        <Button label="Concluir" severity="warning" onClick={handleClose} className="w-full" outlined />
                    </div>
                );

            default: return null;
        }
    };

    return (
        <Dialog
            header={headerElement}
            visible={visible}
            style={{ width: '90vw', maxWidth: '450px' }}
            onHide={handleClose}
            draggable={false}
            blockScroll
        >
            {renderContent()}
        </Dialog>
    );
};

export default ScamWizard;