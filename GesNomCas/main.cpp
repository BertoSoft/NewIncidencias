#include "mainwindow.h"
#include <QApplication>

int main(int argc, char *argv[]){
    QApplication    a(argc, argv);
    MainWindow      w;

    w.setWindowTitle(w.NOMBRE_PROGRAMA);
    w.setWindowIcon(QIcon(":/icono.png"));
    w.showMaximized();
    w.show();

    return a.exec();
}

