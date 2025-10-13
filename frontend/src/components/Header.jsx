const Header = () => {
  return (
    <header className="bg-white shadow-2 px-4 py-4">
      <div className="max-w-screen-xl mx-auto flex align-items-center gap-3">
        <div className="flex align-items-center justify-content-center border-circle bg-blue-50" 
             style={{ width: '50px', height: '50px' }}>
          <i className="pi pi-shield text-3xl text-blue-600"></i>
        </div>
        <div>
          <h1 className="m-0 text-2xl font-bold text-900">ScamGuard</h1>
          <p className="m-0 text-sm text-600">Verificador de Segurança Digital</p>
        </div>
      </div>
    </header>
  );
};

export default Header;