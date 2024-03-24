package telkomedics.models;

import telkomedics.helper.TelkomedicsException;

public abstract class Pengguna {
    public abstract void setId(String id) throws TelkomedicsException;
    public abstract void setNama(String nama) throws TelkomedicsException;
    public abstract void setPassword(String password) throws TelkomedicsException;
}
