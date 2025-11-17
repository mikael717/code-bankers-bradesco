import {Card} from 'primereact/card';

const StatsCard = ({stats = {safe: 0, warning: 0, unsafe: 0}}) => {
    const total = stats.safe + stats.warning + stats.unsafe;
    const safePercentage = total > 0 ? Math.round((stats.safe / total) * 100) : 0;
    const warningPercentage = total > 0 ? Math.round((stats.warning / total) * 100) : 0;
    const unsafePercentage = total > 0 ? Math.round((stats.unsafe / total) * 100) : 0;

    return (
        <div className="grid">
            <div className="col-12 md:col-4">
                <Card style={{
                    border: '1px solid #d1fae5',
                    backgroundColor: '#f0fdf4',
                    borderRadius: '12px',
                    boxShadow: '0 1px 2px rgba(0,0,0,0.05)'
                }}>
                    <div className="flex align-items-center justify-content-between">
                        <div>
                            <div className="text-green-600 font-medium mb-1 text-xs">Seguros</div>
                            <div className="text-3xl font-bold text-green-700">{stats.safe}</div>
                            <div className="text-green-600 text-xs">{safePercentage}%</div>
                        </div>
                        <div style={{width: '48px', height: '48px', backgroundColor: '#d1fae5', borderRadius: '50%'}}
                             className="flex align-items-center justify-content-center">
                            <i className="pi pi-check-circle text-2xl text-green-600"></i>
                        </div>
                    </div>
                </Card>
            </div>

            <div className="col-12 md:col-4">
                <Card style={{
                    border: '1px solid #fed7aa',
                    backgroundColor: '#fffbeb',
                    borderRadius: '12px',
                    boxShadow: '0 1px 2px rgba(0,0,0,0.05)'
                }}>
                    <div className="flex align-items-center justify-content-between">
                        <div>
                            <div className="text-orange-600 font-medium mb-1 text-xs">Atenção</div>
                            <div className="text-3xl font-bold text-orange-700">{stats.warning}</div>
                            <div className="text-orange-600 text-xs">{warningPercentage}%</div>
                        </div>
                        <div style={{width: '48px', height: '48px', backgroundColor: '#fed7aa', borderRadius: '50%'}}
                             className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle text-2xl text-orange-600"></i>
                        </div>
                    </div>
                </Card>
            </div>

            <div className="col-12 md:col-4">
                <Card style={{
                    border: '1px solid #fecaca',
                    backgroundColor: '#fef2f2',
                    borderRadius: '12px',
                    boxShadow: '0 1px 2px rgba(0,0,0,0.05)'
                }}>
                    <div className="flex align-items-center justify-content-between">
                        <div>
                            <div className="text-red-600 font-medium mb-1 text-xs">Perigo</div>
                            <div className="text-3xl font-bold text-red-700">{stats.unsafe}</div>
                            <div className="text-red-600 text-xs">{unsafePercentage}%</div>
                        </div>
                        <div style={{width: '48px', height: '48px', backgroundColor: '#fecaca', borderRadius: '50%'}}
                             className="flex align-items-center justify-content-center">
                            <i className="pi pi-times-circle text-2xl text-red-600"></i>
                        </div>
                    </div>
                </Card>
            </div>
        </div>
    );
};

export default StatsCard;
