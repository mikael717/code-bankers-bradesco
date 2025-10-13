import { useState } from 'react';
import { Card } from 'primereact/card';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Message } from 'primereact/message';

const VerificationForm = ({ onVerify, loading }) => {
  const [itemType, setItemType] = useState('PHONE');
  const [itemValue, setItemValue] = useState('');
  const [error, setError] = useState('');

  const itemTypes = [
    { label: 'Telefone', value: 'PHONE', icon: 'pi pi-phone' },
    { label: 'E-mail', value: 'EMAIL', icon: 'pi pi-envelope' },
    { label: 'URL', value: 'URL', icon: 'pi pi-link' }
  ];

  const getPlaceholder = () => {
    switch(itemType) {
      case 'PHONE':
        return 'Ex: (11) 99999-9999';
      case 'EMAIL':
        return 'Ex: exemplo@email.com';
      case 'URL':
        return 'Ex: https://www.exemplo.com';
      default:
        return '';
    }
  };

  const validateInput = () => {
    if (!itemValue.trim()) {
      setError('Por favor, preencha o campo');
      return false;
    }

    if (itemType === 'EMAIL') {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(itemValue)) {
        setError('E-mail inválido');
        return false;
      }
    }

    if (itemType === 'URL') {
      try {
        new URL(itemValue);
      } catch {
        setError('URL inválida');
        return false;
      }
    }

    setError('');
    return true;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateInput()) {
      onVerify({ itemType, itemValue });
    }
  };

  return (
    <Card className="shadow-3">
      <div className="text-center mb-5">
        <div className="inline-flex align-items-center justify-content-center bg-blue-50 border-circle mb-3" style={{ width: '70px', height: '70px' }}>
          <i className="pi pi-search text-4xl text-blue-600"></i>
        </div>
        <h2 className="text-3xl font-bold text-900 mb-2">Verificador de Golpes</h2>
        <p className="text-600">Verifique a segurança de telefones, e-mails e URLs</p>
      </div>

      <form onSubmit={handleSubmit} className="flex flex-column gap-4">
        <div className="field">
          <label className="block text-900 font-medium mb-3">
            Tipo de Verificação
          </label>
          <div className="grid">
            {itemTypes.map((type) => (
              <div key={type.value} className="col-12 md:col-4">
                <Button
                  type="button"
                  onClick={() => {
                    setItemType(type.value);
                    setItemValue('');
                    setError('');
                  }}
                  className="w-full p-3"
                  outlined={itemType !== type.value}
                  severity={itemType === type.value ? 'info' : 'secondary'}
                >
                  <div className="flex flex-column align-items-center gap-2">
                    <i className={`${type.icon} text-2xl`}></i>
                    <span className="font-semibold">{type.label}</span>
                  </div>
                </Button>
              </div>
            ))}
          </div>
        </div>

        <div className="field">
          <label htmlFor="itemValue" className="block text-900 font-medium mb-2">
            {itemTypes.find(t => t.value === itemType)?.label}
          </label>
          <InputText
            id="itemValue"
            value={itemValue}
            onChange={(e) => {
              setItemValue(e.target.value);
              setError('');
            }}
            placeholder={getPlaceholder()}
            className="w-full"
          />
        </div>

        {error && (
          <Message severity="error" text={error} className="w-full" />
        )}

        <Button
          type="submit"
          label="Verificar"
          icon={loading ? 'pi pi-spin pi-spinner' : 'pi pi-search'}
          className="w-full"
          disabled={loading}
        />
      </form>
    </Card>
  );
};

export default VerificationForm;
