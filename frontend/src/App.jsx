import { useState } from 'react';
import { Toast } from 'primereact/toast';
import { useRef } from 'react';
import Header from './components/Header';
import Footer from './components/Footer';
import VerificationForm from './components/VerificationForm';
import ResultCard from './components/ResultCard';
import HistoryPanel from './components/HistoryPanel';
import StatsCard from './components/StatsCard';
import InfoSection from './components/InfoSection';
import { verifyItem } from './services/api';

const App = () => {
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [history, setHistory] = useState([]);
  const [stats, setStats] = useState({ safe: 0, warning: 0, unsafe: 0 });
  const toast = useRef(null);

  const handleVerify = async (data) => {
    setLoading(true);
    setResult(null);

    try {
      const response = await verifyItem(data.itemType, data.itemValue);
      
      const newResult = {
        ...response,
        timestamp: new Date().toISOString(),
      };

      setResult(newResult);
      
      setHistory(prev => [newResult, ...prev]);
      
      setStats(prev => ({
        safe: prev.safe + (response.verdict === 'SAFE' ? 1 : 0),
        warning: prev.warning + (response.verdict === 'WARNING' ? 1 : 0),
        unsafe: prev.unsafe + (response.verdict === 'UNSAFE' ? 1 : 0),
      }));

      toast.current.show({
        severity: response.verdict === 'SAFE' ? 'success' : response.verdict === 'WARNING' ? 'warn' : 'error',
        summary: 'Verificação Concluída',
        detail: 'Resultado disponível',
        life: 3000,
      });

    } catch (error) {
      toast.current.show({
        severity: 'error',
        summary: 'Erro',
        detail: error.message,
        life: 5000,
      });
    } finally {
      setLoading(false);
    }
  };

  const handleCloseResult = () => {
    setResult(null);
  };

  return (
    <div className="min-h-screen flex flex-column" style={{ backgroundColor: '#f8f9fa' }}>
      <Toast ref={toast} />
      
      <Header />

      <main className="flex-grow-1">
        <div className="max-w-screen-xl mx-auto p-4 py-5">
          
          {stats.safe + stats.warning + stats.unsafe > 0 && (
            <div className="mb-4">
              <StatsCard stats={stats} />
            </div>
          )}

          <div className="grid">
            <div className="col-12 lg:col-6">
              {!result ? (
                <VerificationForm onVerify={handleVerify} loading={loading} />
              ) : (
                <ResultCard result={result} onClose={handleCloseResult} />
              )}
            </div>

            <div className="col-12 lg:col-6">
              <InfoSection />
            </div>
          </div>

          {history.length > 0 && (
            <div className="mt-4">
              <HistoryPanel history={history} />
            </div>
          )}
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default App;
