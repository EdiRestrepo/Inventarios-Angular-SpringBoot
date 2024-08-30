package eero.inventarios.controlador;

import eero.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import eero.inventarios.modelo.Producto;
import eero.inventarios.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//http://localhost:8080/inventario-app
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")  //Para dar permisos a la dirección ip desde donde se van a hacer las peticiones (desde Angular)
public class ProductoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class); //atributo para enviar información a la consola

    @Autowired
    private ProductoServicio productoServicio;

    //http://localhost:8080/inventario-app/productos
    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        List<Producto> productos = this.productoServicio.listarProductos();
        logger.info("Productos obtenidos");
        productos.forEach(producto -> logger.info(producto.toString()));
        return productos;
    }

    @PostMapping("/productos")
    public  Producto agregarProducto(@RequestBody Producto producto){
        logger.info("Prodcuto a agregar: "+producto);
        return this.productoServicio.guardarProducto(producto);
    }

    @GetMapping("productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id){
        Producto producto = this.productoServicio.buscarProductoPorId(id);

        if(producto != null)
            return  ResponseEntity.ok(producto);
        else
          throw   new RecursoNoEncontradoExcepcion("No se encontro el id: "+id);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id, @RequestBody Producto productoRecibido){

        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if(producto == null)
            throw  new RecursoNoEncontradoExcepcion("No se encontro el id: "+id);
        producto.setDescripcion(productoRecibido.getDescripcion());
        producto.setPrecio(productoRecibido.getPrecio());
        producto.setExistencia(productoRecibido.getExistencia());
        this.productoServicio.guardarProducto(producto);
        return  ResponseEntity.ok(producto);

    }

    @DeleteMapping("productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eleiminarProducto(@PathVariable int id){

        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if(producto == null)
            throw  new RecursoNoEncontradoExcepcion("No se encontro el id: "+id);
        this.productoServicio.eleminarProductoPorId(producto.getIdProducto());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
