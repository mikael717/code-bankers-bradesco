const Header = () => {
  return (
    <header style={{ backgroundColor: '#ffffff', borderBottom: '1px solid #e5e7eb' }}>
      <div style={{ maxWidth: '1100px', margin: '0 auto', padding: '1.5rem 2rem' }}>
        <div className="flex align-items-center justify-content-center gap-3">
          <div className="flex align-items-center justify-content-center border-circle" 
               style={{ width: '48px', height: '48px', backgroundColor: '#3b82f6' }}>
            <i className="pi pi-shield text-2xl text-white"></i>
          </div>
          <div className="text-center">
            <h1 className="m-0 text-2xl font-bold text-900">ScamGuard</h1>
            <p className="m-0 text-xs text-500">Verificação de Segurança Digital</p>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;