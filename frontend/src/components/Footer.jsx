const Footer = () => {
  return (
    <footer className="bg-white border-top-1 surface-border py-4 px-4 mt-6">
      <div className="max-w-screen-xl mx-auto flex flex-column md:flex-row justify-content-between align-items-center gap-3">
        <div className="flex align-items-center gap-2">
          <i className="pi pi-shield text-xl text-blue-600"></i>
          <span className="font-semibold text-700">ScamGuard</span>
        </div>
        
        <div className="text-600 text-sm">
          Proteção em tempo real contra fraudes digitais
        </div>
        
        <div className="text-500 text-sm">
          © 2025 Todos os direitos reservados
        </div>
      </div>
    </footer>
  );
};

export default Footer;
