package com.ues.sv.proyecto.controladministrativoapp.rest.conf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.AlumnoRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.CicloRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.CoordinadorRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.CursoRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.DetalleRevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.DocenteRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.EncargadoImpresionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.EvaluacionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ImagenRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ImpresionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.InscripcionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.MateriaRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.MotivoErrorImpresionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ParametrosRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ParticipanteRevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.PersonaRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.RevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.SolicitudRevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.TipoEvaluacionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.UsuarioRestServiceInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OkHttpClientInstance {

    private static volatile OkHttpClient OkHtttpInstance;
    private static volatile AlumnoRestServiceInterface alumnoRestServiceInstance;
    private static volatile CicloRestServiceInterface cicloRestServiceInterface;
    private static volatile CoordinadorRestServiceInterface coordinadorRestServiceInterface;
    private static volatile CursoRestServiceInterface cursoRestServiceInterface;
    private static volatile DetalleRevisionRestServiceInterface detalleRevisionRestServiceInterface;
    private static volatile DocenteRestServiceInterface docenteRestServiceInterface;
    private static volatile EncargadoImpresionRestServiceInterface encargadoImpresionRestServiceInterface;
    private static volatile EvaluacionRestServiceInterface evaluacionRestServiceInterface;
    private static volatile ImagenRestServiceInterface imagenRestServiceInterface;
    private static volatile ImpresionRestServiceInterface impresionRestServiceInterface;
    private static volatile InscripcionRestServiceInterface inscripcionRestServiceInterface;
    private static volatile MateriaRestServiceInterface materiaRestServiceInterface;
    private static volatile MotivoErrorImpresionRestServiceInterface motivoErrorImpresionRestServiceInterface;
    private static volatile ParametrosRestServiceInterface parametrosRestServiceInterface;
    private static volatile ParticipanteRevisionRestServiceInterface participanteRevisionRestServiceInterface;
    private static volatile PersonaRestServiceInterface personaRestServiceInterface;
    private static volatile RevisionRestServiceInterface revisionRestServiceInterface;
    private static volatile SolicitudRevisionRestServiceInterface solicitudRevisionRestServiceInterface;
    private static volatile TipoEvaluacionRestServiceInterface tipoEvaluacionRestServiceInterface;
    private static volatile UsuarioRestServiceInterface usuarioRestServiceInterface;


    public static OkHttpClient getOkHtttpInstance() {
        if (OkHtttpInstance == null) {
            synchronized (OkHttpClient.class) {
                if (OkHtttpInstance == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHtttpInstance = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                }
            }
        }
        return OkHtttpInstance;
    }


    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(OkHttpClientInstance.getOkHtttpInstance())
                .baseUrl(ApiData.API1_URL)
                .build();
    }


    public static AlumnoRestServiceInterface alumnoRestService() {
        if (alumnoRestServiceInstance == null) {
            synchronized (AlumnoRestServiceInterface.class) {
                if (alumnoRestServiceInstance == null) {
                    alumnoRestServiceInstance = OkHttpClientInstance.getRetrofitInstance()
                            .create(AlumnoRestServiceInterface.class);
                }
            }
        }
        return alumnoRestServiceInstance;
    }

    public static CicloRestServiceInterface cicloRestServiceInterface() {
        if (cicloRestServiceInterface == null) {
            synchronized (CicloRestServiceInterface.class) {
                if (cicloRestServiceInterface == null) {
                    cicloRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(CicloRestServiceInterface.class);
                }
            }
        }
        return cicloRestServiceInterface;
    }

    public static CoordinadorRestServiceInterface coordinadorRestServiceInterface() {
        if (coordinadorRestServiceInterface == null) {
            synchronized (CoordinadorRestServiceInterface.class) {
                if (coordinadorRestServiceInterface == null) {
                    coordinadorRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(CoordinadorRestServiceInterface.class);
                }
            }
        }
        return coordinadorRestServiceInterface;
    }

    public static CursoRestServiceInterface cursoRestServiceInterface() {
        if (cursoRestServiceInterface == null) {
            synchronized (CursoRestServiceInterface.class) {
                if (cursoRestServiceInterface == null) {
                    cursoRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(CursoRestServiceInterface.class);
                }
            }
        }
        return cursoRestServiceInterface;
    }

    public static DetalleRevisionRestServiceInterface detalleRevisionRestServiceInterface() {
        if (detalleRevisionRestServiceInterface == null) {
            synchronized (DetalleRevisionRestServiceInterface.class) {
                if (detalleRevisionRestServiceInterface == null) {
                    detalleRevisionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(DetalleRevisionRestServiceInterface.class);
                }
            }
        }
        return detalleRevisionRestServiceInterface;
    }

    public static DocenteRestServiceInterface docenteRestServiceInterface() {
        if (docenteRestServiceInterface == null) {
            synchronized (DocenteRestServiceInterface.class) {
                if (docenteRestServiceInterface == null) {
                    docenteRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(DocenteRestServiceInterface.class);
                }
            }
        }
        return docenteRestServiceInterface;
    }

    public static EncargadoImpresionRestServiceInterface encargadoImpresionRestServiceInterface() {
        if (encargadoImpresionRestServiceInterface == null) {
            synchronized (EncargadoImpresionRestServiceInterface.class) {
                if (encargadoImpresionRestServiceInterface == null) {
                    encargadoImpresionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(EncargadoImpresionRestServiceInterface.class);
                }
            }
        }
        return encargadoImpresionRestServiceInterface;
    }

    public static EvaluacionRestServiceInterface evaluacionRestServiceInterface() {
        if (evaluacionRestServiceInterface == null) {
            synchronized (EvaluacionRestServiceInterface.class) {
                if (evaluacionRestServiceInterface == null) {
                    evaluacionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(EvaluacionRestServiceInterface.class);
                }
            }
        }
        return evaluacionRestServiceInterface;
    }

    public static ImagenRestServiceInterface imagenRestServiceInterface() {
        if (imagenRestServiceInterface == null) {
            synchronized (ImagenRestServiceInterface.class) {
                if (imagenRestServiceInterface == null) {
                    imagenRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(ImagenRestServiceInterface.class);
                }
            }
        }
        return imagenRestServiceInterface;
    }

    public static ImpresionRestServiceInterface impresionRestServiceInterface() {
        if (impresionRestServiceInterface == null) {
            synchronized (ImpresionRestServiceInterface.class) {
                if (impresionRestServiceInterface == null) {
                    impresionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(ImpresionRestServiceInterface.class);
                }
            }
        }
        return impresionRestServiceInterface;
    }

    public static InscripcionRestServiceInterface inscripcionRestServiceInterface() {
        if (inscripcionRestServiceInterface == null) {
            synchronized (InscripcionRestServiceInterface.class) {
                if (inscripcionRestServiceInterface == null) {
                    inscripcionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(InscripcionRestServiceInterface.class);
                }
            }
        }
        return inscripcionRestServiceInterface;
    }

    public static MateriaRestServiceInterface materiaRestServiceInterface() {
        if (materiaRestServiceInterface == null) {
            synchronized (MateriaRestServiceInterface.class) {
                if (materiaRestServiceInterface == null) {
                    materiaRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(MateriaRestServiceInterface.class);
                }
            }
        }
        return materiaRestServiceInterface;
    }

    public static MotivoErrorImpresionRestServiceInterface motivoErrorImpresionRestServiceInterface() {
        if (motivoErrorImpresionRestServiceInterface == null) {
            synchronized (MotivoErrorImpresionRestServiceInterface.class) {
                if (motivoErrorImpresionRestServiceInterface == null) {
                    motivoErrorImpresionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(MotivoErrorImpresionRestServiceInterface.class);
                }
            }
        }
        return motivoErrorImpresionRestServiceInterface;
    }

    public static ParametrosRestServiceInterface parametrosRestServiceInterface() {
        if (parametrosRestServiceInterface == null) {
            synchronized (ParametrosRestServiceInterface.class) {
                if (parametrosRestServiceInterface == null) {
                    parametrosRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(ParametrosRestServiceInterface.class);
                }
            }
        }
        return parametrosRestServiceInterface;
    }

    public static ParticipanteRevisionRestServiceInterface participanteRevisionRestServiceInterface() {
        if (participanteRevisionRestServiceInterface == null) {
            synchronized (ParticipanteRevisionRestServiceInterface.class) {
                if (participanteRevisionRestServiceInterface == null) {
                    participanteRevisionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(ParticipanteRevisionRestServiceInterface.class);
                }
            }
        }
        return participanteRevisionRestServiceInterface;
    }

    public static PersonaRestServiceInterface personaRestServiceInterface() {
        if (personaRestServiceInterface == null) {
            synchronized (PersonaRestServiceInterface.class) {
                if (personaRestServiceInterface == null) {
                    personaRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(PersonaRestServiceInterface.class);
                }
            }
        }
        return personaRestServiceInterface;
    }

    public static RevisionRestServiceInterface revisionRestServiceInterface() {
        if (revisionRestServiceInterface == null) {
            synchronized (RevisionRestServiceInterface.class) {
                if (revisionRestServiceInterface == null) {
                    revisionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(RevisionRestServiceInterface.class);
                }
            }
        }
        return revisionRestServiceInterface;
    }

    public static SolicitudRevisionRestServiceInterface solicitudRevisionRestServiceInterface() {
        if (solicitudRevisionRestServiceInterface == null) {
            synchronized (SolicitudRevisionRestServiceInterface.class) {
                if (solicitudRevisionRestServiceInterface == null) {
                    solicitudRevisionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(SolicitudRevisionRestServiceInterface.class);
                }
            }
        }
        return solicitudRevisionRestServiceInterface;
    }

    public static TipoEvaluacionRestServiceInterface tipoEvaluacionRestServiceInterface() {
        if (tipoEvaluacionRestServiceInterface == null) {
            synchronized (TipoEvaluacionRestServiceInterface.class) {
                if (tipoEvaluacionRestServiceInterface == null) {
                    tipoEvaluacionRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(TipoEvaluacionRestServiceInterface.class);
                }
            }
        }
        return tipoEvaluacionRestServiceInterface;
    }

    public static UsuarioRestServiceInterface usuarioRestServiceInterface() {
        if (usuarioRestServiceInterface == null) {
            synchronized (UsuarioRestServiceInterface.class) {
                if (usuarioRestServiceInterface == null) {
                    usuarioRestServiceInterface = OkHttpClientInstance.getRetrofitInstance()
                            .create(UsuarioRestServiceInterface.class);
                }
            }
        }
        return usuarioRestServiceInterface;
    }

}
