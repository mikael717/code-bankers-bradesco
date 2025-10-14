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
    <Card style={{ border: '1px solid #e5e7eb', boxShadow: '0 1px 3px rgba(0,0,0,0.08)', borderRadius: '12px' }}>
      <div className="mb-4">
        <h2 className="text-2xl font-bold text-900 mb-2">Verificar Segurança</h2>
        <p className="text-600 text-sm m-0">Selecione o tipo e insira os dados para verificação</p>
      </div>

      <form onSubmit={handleSubmit} className="flex flex-column gap-4">
        <div className="field">
          <label className="block text-900 font-medium mb-3 text-sm">
            Tipo de Verificação
          </label>
          <div className="flex gap-2">
            {itemTypes.map((type) => (
              <div key={type.value} className="flex-1">
                <div
                  onClick={() => {
                    setItemType(type.value);
                    setItemValue('');
                    setError('');
                  }}
                  className="p-3 text-center cursor-pointer transition-all transition-duration-200"
                  style={{
                    backgroundColor: itemType === type.value ? '#3b82f6' : '#ffffff',
                    color: itemType === type.value ? '#ffffff' : '#6b7280',
                    border: `1px solid ${itemType === type.value ? '#3b82f6' : '#e5e7eb'}`,
                    borderRadius: '8px'
                  }}
                >
                  <i className={`${type.icon} text-2xl mb-2 block`}></i>
                  <span className="text-sm font-medium">{type.label}</span>
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="field">
          <label htmlFor="itemValue" className="block text-900 font-medium mb-2 text-sm">
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
            style={{ padding: '0.75rem', fontSize: '0.95rem', borderRadius: '8px' }}
          />
        </div>

        {error && (
          <Message severity="error" text={error} className="w-full" style={{ borderRadius: '8px' }} />
        )}

        <Button
          type="submit"
          label={loading ? "Verificando..." : "Verificar"}
          icon={loading ? 'pi pi-spin pi-spinner' : 'pi pi-search'}
          className="w-full font-semibold"
          style={{ 
            padding: '0.75rem', 
            backgroundColor: '#3b82f6', 
            border: 'none',
            borderRadius: '8px'
          }}
          disabled={loading}
        />
      </form>
    </Card>
  );
};

export default VerificationForm;
