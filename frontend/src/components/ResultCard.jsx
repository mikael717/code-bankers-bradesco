import { useState } from 'react';
import { Card } from 'primereact/card';
import { Tag } from 'primereact/tag'; // Verifique se esta importação está correta
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { Dialog } from 'primereact/dialog';
import { InputTextarea } from 'primereact/inputtextarea';
import { Toast } from 'primereact/toast';
import { useRef } from 'react';

const ResultCard = ({ result, requestedItemType, requestedValue, onClose }) => {
    const [showReportDialog, setShowReportDialog] = useState(false);
    const [reportReason, setReportReason] = useState('');
    const [reporting, setReporting] = useState(false);
    const toast = useRef(null);

    const config = (() => {
        const verdict = result?.verdict || 'MEDIUM_RISK'; // Default para não quebrar
        switch (verdict) {
            case 'SAFE':
                return { severity: 'success', icon: "pi pi-check-circle", color: 'text-green-600', bgColor: 'bg-green-50', title: 'Seguro', message: 'Este item está em nossa lista de confiança', progressColor: '#22c55e' }; // Verde sólido
            case 'LOW_RISK':
                return { severity: 'info', icon: "pi pi-info-circle", color: 'text-blue-600', bgColor: 'bg-blue-50', title: 'Baixo Risco', message: 'Alguns indicadores leves, mas provavelmente seguro.', progressColor: '#3b82f6' }; // Azul sólido
            case 'MEDIUM_RISK':
                return { severity: 'warning', icon: "pi pi-exclamation-triangle", color: 'text-yellow-600', bgColor: 'bg-yellow-50', title: 'Atenção', message: 'Este item requer cuidado. Proceda com cautela.', progressColor: '#f59e0b' }; // Amarelo sólido (Corrigido)
            case 'HIGH_RISK':
                return { severity: 'danger', icon: "pi pi-exclamation-circle", color: 'text-orange-600', bgColor: 'bg-orange-50', title: 'Alto Risco', message: 'Muitos indícios de fraude detectados.', progressColor: '#fb923c' }; // Laranja sólido (Corrigido)
            case 'CONFIRMED_SCAM':
                return { severity: 'danger', icon: "pi pi-times-circle", color: 'text-red-600', bgColor: 'bg-red-50', title: 'Golpe Confirmado', message: 'Este item está na lista negra de fraudes.', progressColor: '#ef4444' }; // Vermelho sólido
            default:
                return { severity: 'warning', icon: 'pi pi-question', color: 'text-gray-600', bgColor: 'bg-gray-50', title: 'Desconhecido', message: 'Status não identificado.', progressColor: '#6b7280' }; // Cinza sólido
        }
    })();

    const displayItemType = result?.itemType || requestedItemType || '';
    const displayValue = result?.itemValue || requestedValue || '';
    const displayScore = result?.riskScore !== undefined ? result.riskScore : '-';

    // Função que chama o Backend
    const handleReportSubmit = async () => {
        setReporting(true);
        try {
            const response = await fetch('http://localhost:8080/report', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    itemType: displayItemType,
                    itemValue: displayValue,
                    // O backend espera VerificationRequest, que nao tem "reason" ainda no DTO principal,
                    // Se você quiser mandar reason, precisará atualizar o DTO no Java ou mandar na query string.
                    // Por enquanto, vamos mandar o básico para somar pontos.
                })
            });

            if (response.ok) {
                toast.current.show({ severity: 'success', summary: 'Sucesso', detail: 'Denúncia enviada! Obrigado por colaborar.', life: 3000 });
                setTimeout(() => {
                    setShowReportDialog(false);
                    setReportReason('');
                    onClose(); // Fecha o card e volta pro form
                }, 2000);
            } else {
                throw new Error('Erro ao reportar');
            }
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Erro', detail: 'Falha ao enviar denúncia.', life: 3000 });
        } finally {
            setReporting(false);
        }
    };

    return (
        <>
            <Toast ref={toast} />
            <Card className="shadow-3">
                <div className="text-center">
                    <div className={`inline-flex align-items-center justify-content-center ${config.bgColor} border-circle mb-4`} style={{ width: '80px', height: '80px' }}>
                        <i className={`${config.icon} text-5xl ${config.color}`}></i>
                    </div>

                    <h2 className={`text-3xl font-bold mb-2 ${config.color}`}>{config.title}</h2>
                    <p className="text-600 mb-4">{config.message}</p>

                    {/* Barra de Pontos (Risk Score) */}
                    <div className="mb-4 px-4">
                        <div className="flex justify-content-between mb-1">
                            <span className="text-sm text-600">Nível de Risco</span>
                            <span className="text-sm font-bold">{displayScore}/100</span>
                        </div>
                        <div className="w-full bg-gray-200 border-round h-1rem">
                            <div
                                className={`h-full border-round`}
                                style={{
                                    width: `${Math.min(displayScore, 100)}%`,
                                    // AQUI ESTAVA O PROBLEMA! Usamos config.progressColor diretamente.
                                    backgroundColor: config.progressColor
                                }}
                            ></div>
                        </div>
                    </div>

                    <Divider />

                    <div className="text-left my-4">
                        <div className="flex align-items-center justify-content-between mb-3 p-3 surface-50 border-round">
                            <span className="font-medium text-700">Tipo:</span>
                            <Tag value={displayItemType} severity="info" /> {/* Tag de tipo, pode ser alterada para seguir o config.severity se quiser */}
                        </div>

                        <div className="flex align-items-start justify-content-between mb-3 p-3 surface-50 border-round">
                            <span className="font-medium text-700">Valor:</span>
                            <span className="text-900 font-mono text-right" style={{ wordBreak: 'break-all', maxWidth: '60%' }}>
                                {displayValue}
                            </span>
                        </div>

                    </div>

                    <div className="flex gap-2">
                        <Button
                            label="Nova Verificação"
                            icon="pi pi-refresh"
                            onClick={onClose}
                            className="flex-1"
                            outlined
                        />
                        <Button
                            label="Reportar como Golpe"
                            icon="pi pi-flag"
                            className="flex-1"
                            severity="danger"
                            outlined
                            onClick={() => setShowReportDialog(true)}
                        />
                    </div>
                </div>
            </Card>

            {/* Modal de Denúncia */}
            <Dialog
                header="Reportar Golpe"
                visible={showReportDialog}
                style={{ width: '90vw', maxWidth: '500px' }}
                onHide={() => setShowReportDialog(false)}
                footer={
                    <div>
                        <Button label="Cancelar" icon="pi pi-times" onClick={() => setShowReportDialog(false)} className="p-button-text" />
                        <Button label="Enviar Denúncia" icon="pi pi-check" onClick={handleReportSubmit} autoFocus loading={reporting} severity="danger" />
                    </div>
                }
            >
                <div className="flex flex-column gap-3">
                    <p className="m-0 text-600">
                        Você está reportando <strong>{displayValue}</strong> como suspeito.
                        Isso ajudará a aumentar a pontuação de risco deste item para outros usuários.
                    </p>
                    <div className="field">
                        <label htmlFor="reason" className="block mb-2 font-medium">Motivo (Opcional)</label>
                        <InputTextarea
                            id="reason"
                            value={reportReason}
                            onChange={(e) => setReportReason(e.target.value)}
                            rows={3}
                            className="w-full"
                            placeholder="Ex: Me pediu PIX, link falso, se passou pelo banco..."
                        />
                    </div>
                </div>
            </Dialog>
        </>
    );
};

export default ResultCard;