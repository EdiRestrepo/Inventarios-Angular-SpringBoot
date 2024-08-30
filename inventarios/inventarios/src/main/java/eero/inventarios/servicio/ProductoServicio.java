package eero.inventarios.servicio;

import eero.inventarios.modelo.Producto;
import eero.inventarios.repositorio.IProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio implements IProductoServicio{

    @Autowired
    private IProductoRepositorio iProductoRepositorio;

    @Override
    public List<Producto> listarProductos() {
        return this.iProductoRepositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Integer idProducto) {
        Producto producto = this.iProductoRepositorio.findById(idProducto).orElse(null); //Regresa un Objeto de tipo Opcional

        return producto;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
       return this.iProductoRepositorio.save(producto); //Si el idProducto es igual a null se hace in insert en la base de datos
                                                    //Si es diferente de null se hace un update
    }

    @Override
    public void eleminarProductoPorId(Integer idProducto) {
        this.iProductoRepositorio.deleteById(idProducto);
    }
}
