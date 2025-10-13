import { Card } from 'primereact/card';

const InfoSection = () => {
  const tips = [
    {
      icon: 'pi pi-exclamation-triangle',
      text: 'Nunca forneça dados pessoais por telefone, e-mail ou links desconhecidos',
      color: 'text-orange-600',
      bgColor: 'bg-orange-50'
    },
    {
      icon: 'pi pi-shield',
      text: 'Verifique sempre a autenticidade antes de clicar em qualquer link',
      color: 'text-blue-600',
      bgColor: 'bg-blue-50'
    },
    {
      icon: 'pi pi-phone',
      text: 'Desconfie de ligações solicitando senhas ou códigos de verificação',
      color: 'text-red-600',
      bgColor: 'bg-red-50'
    },
    {
      icon: 'pi pi-lock',
      text: 'Em caso de dúvida, entre em contato diretamente com a instituição oficial',
      color: 'text-green-600',
      bgColor: 'bg-green-50'
    }
  ];

  return (
    <Card className="shadow-3">
      <div className="mb-3">
        <h3 className="text-xl font-bold text-900 mb-2 flex align-items-center gap-2">
          <i className="pi pi-info-circle text-blue-600"></i>
          Dicas de Segurança
        </h3>
        <p className="text-600 text-sm m-0">Proteja-se contra golpes digitais</p>
      </div>

      <div className="flex flex-column gap-3">
        {tips.map((tip, index) => (
          <div key={index} className={`flex gap-3 p-3 border-round ${tip.bgColor}`}>
            <div className="flex-shrink-0">
              <i className={`${tip.icon} ${tip.color} text-xl`}></i>
            </div>
            <p className="text-700 m-0 text-sm line-height-3">{tip.text}</p>
          </div>
        ))}
      </div>
    </Card>
  );
};

export default InfoSection;
