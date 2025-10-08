package com.cronossuite.cadastros.repository.rlcl.usuario.beans.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cronossuite.cadastros.repository.rlcl.usuario.beans.ServiceUsers;
import com.cronossuite.cadastros.repository.rlcl.usuario.saida.SaiUsers;

@Service
public class DaoServiceUsers  implements ServiceUsers{

    @Override
    public List<SaiUsers> ReadAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ReadAll'");
    }

    @Override
    public SaiUsers ReadById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ReadById'");
    }

    @Override
    public SaiUsers Create(SaiUsers pessoa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Create'");
    }

    @Override
    public SaiUsers Update(SaiUsers pessoa, String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public void Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }
    
}
