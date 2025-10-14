import { Card } from 'primereact/card';

const InfoSection = () => {
  const tips = [
    {
      icon: 'pi pi-exclamation-triangle',
      text: 'Nunca forneça dados pessoais por telefone, e-mail ou links desconhecidos',
      iconColor: '#f59e0b'
    },
    {
      icon: 'pi pi-shield',
      text: 'Verifique sempre a autenticidade antes de clicar em qualquer link',
      iconColor: '#3b82f6'
    },
    {
      icon: 'pi pi-phone',
      text: 'Desconfie de ligações solicitando senhas ou códigos de verificação',
      iconColor: '#ef4444'
    },
    {
      icon: 'pi pi-lock',
      text: 'Em caso de dúvida, entre em contato diretamente com a instituição oficial',
      iconColor: '#10b981'
    }
  ];

  return (
    <Card style={{ border: '1px solid #e5e7eb', boxShadow: '0 1px 3px rgba(0,0,0,0.08)', borderRadius: '12px', height: '100%' }}>
      <div className="mb-4">
        <h3 className="text-lg font-bold text-900 mb-2">Dicas de Segurança</h3>
        <p className="text-600 text-sm m-0">Proteja-se contra golpes digitais</p>
      </div>

      <div className="flex flex-column gap-3">
        {tips.map((tip, index) => (
          <div 
            key={index} 
            className="p-3"
            style={{ 
              backgroundColor: '#f9fafb',
              borderRadius: '8px',
              borderLeft: `3px solid ${tip.iconColor}`
            }}
          >
            <div className="flex gap-3 align-items-start">
              <div className="flex-shrink-0">
                <i className={`${tip.icon} text-lg`} style={{ color: tip.iconColor }}></i>
              </div>
              <p className="text-700 m-0 text-sm line-height-3">{tip.text}</p>
            </div>
          </div>
        ))}
      </div>
    </Card>
  );
};

export default InfoSection;
