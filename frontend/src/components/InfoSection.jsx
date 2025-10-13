import { Card } from 'primereact/card';

const InfoSection = () => {
  const features = [
    {
      icon: 'pi pi-shield',
      title: 'Proteção em Tempo Real',
      description: 'Verificação instantânea contra base de dados atualizada',
      color: 'text-blue-600',
      bgColor: 'bg-blue-50'
    },
    {
      icon: 'pi pi-database',
      title: 'Base de Dados Completa',
      description: 'Milhares de registros de golpes conhecidos',
      color: 'text-indigo-600',
      bgColor: 'bg-indigo-50'
    },
    {
      icon: 'pi pi-bolt',
      title: 'Análise Rápida',
      description: 'Resultados em menos de 1 segundo',
      color: 'text-cyan-600',
      bgColor: 'bg-cyan-50'
    },
    {
      icon: 'pi pi-verified',
      title: 'Confiável',
      description: 'Sistema robusto e seguro',
      color: 'text-teal-600',
      bgColor: 'bg-teal-50'
    }
  ];

  return (
    <Card className="shadow-3">
      <div className="text-center mb-4">
        <h3 className="text-2xl font-bold text-900 mb-2">Por que usar o ScamGuard?</h3>
        <p className="text-600">Sua segurança digital é nossa prioridade</p>
      </div>

      <div className="grid">
        {features.map((feature, index) => (
          <div key={index} className="col-12 md:col-6">
            <div className="flex gap-3 p-3">
              <div className={`${feature.bgColor} border-circle flex align-items-center justify-content-center flex-shrink-0`}
                   style={{ width: '50px', height: '50px' }}>
                <i className={`${feature.icon} text-xl ${feature.color}`}></i>
              </div>
              <div>
                <h4 className="text-lg font-semibold text-900 mb-1">{feature.title}</h4>
                <p className="text-600 m-0 text-sm line-height-3">{feature.description}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </Card>
  );
};

export default InfoSection;
