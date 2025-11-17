import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const verifyItem = async (itemType, itemValue) => {
    try {
        const response = await api.post('/verify', {
            itemType,
            itemValue,
        });
        return response.data;
    } catch (error) {
        if (error.response) {
            throw new Error(error.response.data.message || 'Erro ao verificar item');
        } else if (error.request) {
            throw new Error('Servidor não está respondendo. Verifique se o backend está rodando.');
        } else {
            throw new Error('Erro ao fazer a requisição');
        }
    }
};

export const getStats = async () => {
    const {data} = await api.get('/stats');
    return data;
}

export default api;
