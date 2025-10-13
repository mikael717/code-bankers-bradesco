import { Card } from 'primereact/card';

const StatsCard = ({ stats }) => {
  const total = stats.safe + stats.warning + stats.unsafe;
  const safePercentage = total > 0 ? Math.round((stats.safe / total) * 100) : 0;
  const warningPercentage = total > 0 ? Math.round((stats.warning / total) * 100) : 0;
  const unsafePercentage = total > 0 ? Math.round((stats.unsafe / total) * 100) : 0;

  return (
    <div className="grid">
      <div className="col-12 md:col-4">
        <Card className="shadow-2 bg-green-50 border-1 border-green-200">
          <div className="flex align-items-center justify-content-between">
            <div>
              <div className="text-green-600 font-medium mb-1 text-sm">Seguros</div>
              <div className="text-3xl font-bold text-green-700">{stats.safe}</div>
              <div className="text-green-600 text-xs">{safePercentage}% do total</div>
            </div>
            <div className="bg-green-100 border-circle flex align-items-center justify-content-center" 
                 style={{ width: '50px', height: '50px' }}>
              <i className="pi pi-check-circle text-2xl text-green-600"></i>
            </div>
          </div>
        </Card>
      </div>

      <div className="col-12 md:col-4">
        <Card className="shadow-2 bg-orange-50 border-1 border-orange-200">
          <div className="flex align-items-center justify-content-between">
            <div>
              <div className="text-orange-600 font-medium mb-1 text-sm">Atenção</div>
              <div className="text-3xl font-bold text-orange-700">{stats.warning}</div>
              <div className="text-orange-600 text-xs">{warningPercentage}% do total</div>
            </div>
            <div className="bg-orange-100 border-circle flex align-items-center justify-content-center" 
                 style={{ width: '50px', height: '50px' }}>
              <i className="pi pi-exclamation-triangle text-2xl text-orange-600"></i>
            </div>
          </div>
        </Card>
      </div>

      <div className="col-12 md:col-4">
        <Card className="shadow-2 bg-red-50 border-1 border-red-200">
          <div className="flex align-items-center justify-content-between">
            <div>
              <div className="text-red-600 font-medium mb-1 text-sm">Perigo</div>
              <div className="text-3xl font-bold text-red-700">{stats.unsafe}</div>
              <div className="text-red-600 text-xs">{unsafePercentage}% do total</div>
            </div>
            <div className="bg-red-100 border-circle flex align-items-center justify-content-center" 
                 style={{ width: '50px', height: '50px' }}>
              <i className="pi pi-times-circle text-2xl text-red-600"></i>
            </div>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default StatsCard;
