import {useEffect, useRef, useState} from 'react';
import {Toast} from 'primereact/toast';

import Header from './components/Header';
import Footer from './components/Footer';
import VerificationForm from './components/VerificationForm';
import ResultCard from './components/ResultCard';
import HistoryPanel from './components/HistoryPanel';
import StatsCard from './components/StatsCard';
import InfoSection from './components/InfoSection';
import {verifyItem, getStats} from './services/api';

// função pura para mapear a resposta do backend -> shape usado nos cards
function mapStatsResponse(res) {
    return {
        safe: res?.verdictCounts?.SAFE ?? 0,
        warning: res?.verdictCounts?.REVIEW ?? 0,
        unsafe: res?.verdictCounts?.BLOCK ?? 0,
    };
}

const App = () => {
    const [loading, setLoading] = useState(false);
    const [result, setResult] = useState(null);
    const [history, setHistory] = useState([]);
    const [stats, setStats] = useState({safe: 0, warning: 0, unsafe: 0});
    const toast = useRef(null);


    const refreshStats = async () => {
        try {
            const res = await getStats();
            setStats(mapStatsResponse(res));
        } catch (e) {
            console.error('Falha ao carregar /stats', e);
            // importante: não zere stats em erro
        }
    };

    useEffect(() => {
        refreshStats();
    }, []);

    const handleVerify = async (data) => {
        setLoading(true);
        setResult(null);
        try {
            const response = await verifyItem(data.itemType, data.itemValue);

            const newResult = {
                ...response,
                itemType: data.itemType,
                itemValue: data.itemValue,
                timestamp: new Date().toISOString(),
            };

            setResult(newResult);
            setHistory((prev) => [newResult, ...prev]);

            // atualiza os contadores do topo a partir do backend
            await refreshStats();

            toast.current.show({
                severity:
                    response.verdict === 'SAFE'
                        ? 'success'
                        : response.verdict === 'REVIEW'
                            ? 'warn'
                            : 'error',
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

    const handleCloseResult = () => setResult(null);

    const total = stats.safe + stats.warning + stats.unsafe; // só leitura para exibir ou não os cards

    return (
        <div className="min-h-screen flex flex-column" style={{backgroundColor: '#fafbfc'}}>
            <Toast ref={toast}/>

            <Header/>

            <main className="flex-grow-1 py-5">
                <div style={{maxWidth: '1100px', margin: '0 auto', padding: '0 2rem'}}>
                    {total > 0 && (
                        <div className="mb-5">
                            <StatsCard stats={stats}/>
                        </div>
                    )}

                    <div className="grid">
                        <div className="col-12 lg:col-7 xl:col-7">
                            <div className="pr-0 lg:pr-3">
                                {!result ? (
                                    <VerificationForm onVerify={handleVerify} loading={loading}/>
                                ) : (
                                    <ResultCard result={result} onClose={handleCloseResult}/>
                                )}
                            </div>
                        </div>

                        <div className="col-12 lg:col-5 xl:col-5">
                            <div className="pl-0 lg:pl-3 mt-4 lg:mt-0">
                                <InfoSection/>
                            </div>
                        </div>
                    </div>

                    {history.length > 0 && (
                        <div className="mt-5">
                            <HistoryPanel history={history}/>
                        </div>
                    )}
                </div>
            </main>

            <Footer/>
        </div>
    );
};

export default App;
