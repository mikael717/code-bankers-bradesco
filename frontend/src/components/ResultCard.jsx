import {Card} from 'primereact/card';
import {Tag} from 'primereact/tag';
import {Button} from 'primereact/button';
import {Divider} from 'primereact/divider';

const ResultCard = ({result, requestedItemType, requestedValue, onClose, onAfterVerify}) => {
    const verdict = result?.verdict || 'REVIEW'
    const isSafe = result.verdict === 'SAFE';

    const config = (() => {
        switch (verdict) {
            case 'SAFE':
                return {
                    severity: 'success',
                    icon: "pi pi-check-circle",
                    color: 'text-green-600',
                    bgColor: 'bg-green-50',
                    title: 'Seguro',
                    message: 'Este item está em nossa lista de confiança',
                };
            case 'REVIEW':
                return {
                    severity: 'warning',
                    icon: 'pi pi-exclamation-triangle',
                    color: 'text-orange-600',
                    bgColor: 'bg-orange-50',
                    title: 'Atenção',
                    message: 'Este item requer cuidado. Proceda com cautela',
                };
            default: //block
                return {
                    severity: 'danger',
                    icon: 'pi pi-times-circle',
                    color: 'text-red-600',
                    bgColor: 'bg-red-50',
                    title: 'Perigo — Possível golpe',
                    message: 'Este item foi identificado como suspeito ou malicioso',
                };
        }
    })();

    //prefere o meta se existir; se não usa o que veio pelo formulário
    const displayItemType =
        result?.meta?.itemType || result?.itemType || requestedItemType || '';

    const displayValue =
        result?.meta?.normalizedValue ||
        result?.meta?.originalValue ||
        result?.itemValue ||
        requestedValue ||
        '';

    return (
        <Card className="shadow-3">
            <div className="text-center">
                <div
                    className={`inline-flex align-items-center justify-content-center ${config.bgColor} border-circle mb-4`}
                    style={{width: '80px', height: '80px'}}>
                    <i className={`${config.icon} text-5xl ${config.color}`}></i>
                </div>

                <h2 className={`text-3xl font-bold mb-2 ${config.color}`}>{config.title}</h2>
                <p className="text-600 mb-4">{config.message}</p>

                <Divider/>

                <div className="text-left my-4">
                    <div className="flex align-items-center justify-content-between mb-3 p-3 surface-50 border-round">
                        <span className="font-medium text-700">Tipo:</span>
                        <Tag value={displayItemType} severity="info"/>
                    </div>

                    <div className="flex align-items-start justify-content-between mb-3 p-3 surface-50 border-round">
                        <span className="font-medium text-700">Valor:</span>
                        <span className="text-900 font-mono text-right"
                              style={{wordBreak: 'break-all', maxWidth: '60%'}}>
              {displayValue}
            </span>
                    </div>

                    <div className="flex align-items-center justify-content-between p-3 surface-50 border-round">
                        <span className="font-medium text-700">Status:</span>
                        <Tag value={config.title} severity={config.severity}/>
                    </div>
                </div>

                {!isSafe && (
                    <div className={`p-4 border-round ${config.bgColor} mb-4`}>
                        <div className="flex align-items-start gap-3">
                            <i className={`${config.icon} ${config.color} text-xl`}></i>
                            <div className="text-left">
                                <h4 className={`font-semibold mb-2 ${config.color}`}>Recomendações:</h4>
                                <ul className="text-700 line-height-3 pl-3 m-0">
                                    <li>Não forneça dados pessoais</li>
                                    <li>Não clique em links suspeitos</li>
                                    <li>Entre em contato com instituições oficiais</li>
                                    <li>Reporte esta tentativa de fraude</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                )}

                <div className="flex gap-2">
                    <Button
                        label="Nova Verificação"
                        icon="pi pi-refresh"
                        onClick={onClose}
                        className="flex-1"
                        outlined
                    />
                    <Button
                        label="Reportar"
                        icon="pi pi-flag"
                        className="flex-1"
                        severity="danger"
                        outlined
                        disabled={isSafe}
                    />
                </div>
            </div>
        </Card>
    );
};

export default ResultCard;
