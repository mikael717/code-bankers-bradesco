import { Card } from 'primereact/card';
import { Timeline } from 'primereact/timeline';
import { Tag } from 'primereact/tag';
import { ScrollPanel } from 'primereact/scrollpanel';

const HistoryPanel = ({ history }) => {
  const getVerdictSeverity = (verdict) => {
    switch(verdict) {
      case 'SAFE': return 'success';
      case 'WARNING': return 'warning';
      case 'UNSAFE': return 'danger';
      default: return 'info';
    }
  };

  const getVerdictIcon = (verdict) => {
    switch(verdict) {
      case 'SAFE': return 'pi pi-check-circle';
      case 'WARNING': return 'pi pi-exclamation-triangle';
      case 'UNSAFE': return 'pi pi-times-circle';
      default: return 'pi pi-question-circle';
    }
  };

  const customizedMarker = (item) => {
    return (
      <span className={`flex align-items-center justify-content-center border-circle shadow-3`}
            style={{ 
              width: '3rem', 
              height: '3rem',
              backgroundColor: item.verdict === 'SAFE' ? '#22C55E' : item.verdict === 'WARNING' ? '#F59E0B' : '#EF4444'
            }}>
        <i className={`${getVerdictIcon(item.verdict)} text-white text-xl`}></i>
      </span>
    );
  };

  const customizedContent = (item) => {
    return (
      <Card className="shadow-2 mb-3">
        <div className="flex flex-column gap-2">
          <div className="flex align-items-center justify-content-between">
            <Tag value={item.itemType} severity="info" rounded />
            <Tag value={item.verdict} severity={getVerdictSeverity(item.verdict)} rounded />
          </div>
          <div className="font-mono text-sm text-700 mt-2" style={{ wordBreak: 'break-all' }}>
            {item.itemValue}
          </div>
          <div className="text-xs text-500 mt-2">
            <i className="pi pi-clock mr-2"></i>
            {new Date(item.timestamp).toLocaleString('pt-BR')}
          </div>
        </div>
      </Card>
    );
  };

  if (!history || history.length === 0) {
    return (
      <Card style={{ border: '1px solid #e5e7eb', boxShadow: '0 1px 3px rgba(0,0,0,0.08)', borderRadius: '12px' }}>
        <div className="text-center p-5">
          <i className="pi pi-history text-5xl text-400 mb-3"></i>
          <h3 className="text-xl font-semibold text-700 mb-2">Nenhuma verificação</h3>
          <p className="text-600 m-0">O histórico aparecerá aqui</p>
        </div>
      </Card>
    );
  }

  return (
    <Card style={{ border: '1px solid #e5e7eb', boxShadow: '0 1px 3px rgba(0,0,0,0.08)', borderRadius: '12px' }}>
      <div className="mb-4">
        <h3 className="text-lg font-bold text-900 m-0 mb-1">
          <i className="pi pi-history mr-2"></i>
          Histórico de Verificações
        </h3>
        <p className="text-600 text-sm m-0">Últimas {history.length} verificação(ões)</p>
      </div>

      <ScrollPanel style={{ width: '100%', height: '400px' }}>
        <Timeline 
          value={history} 
          align="left"
          content={customizedContent}
          marker={customizedMarker}
        />
      </ScrollPanel>
    </Card>
  );
};

export default HistoryPanel;
