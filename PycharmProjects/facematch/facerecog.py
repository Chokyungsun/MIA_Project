import dlib,cv2
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as patches
import matplotlib.patheffects as path_effects
import sys

detector=dlib.get_frontal_face_detector()
sp= dlib.shape_predictor('models/shape_predictor_68_face_landmarks.dat')
facerec=dlib.face_recognition_model_v1('models/dlib_face_recognition_resnet_model_v1.dat')

def find_faces(img):
    dets = detector(img,1)


    if len(dets)==0:
        return np.empty(0),np.empty(0),np.empty(0)

    rects, shapes= [], []

    shapes_np=np.zeros((len(dets),68,2), dtype=np.int)
    for k,d in enumerate(dets):
        rect = ((d.left(),d.top()),(d.right(), d.bottom()))
        rects.append(rect)

        shape=sp(img,d)

        for i in range(0,68):
            shapes_np[k][i] = (shape.part(i).x,shape.part(i).y)

        shapes.append(shape)

    return rects,shapes,shapes_np

def encode_faces(img,shapes):
    face_descriptors=[]
    for shape in shapes:
        face_descriptor=facerec.compute_face_descriptor(img,shape)
        face_descriptors.append(np.array(face_descriptor))

    return np.array(face_descriptors)

img_paths={
    'chan':'img/chantest2.jpg',
    'sunny':'img/sunny.jpg',
    'choi':'img/choi.jpg',
    'cho': 'img/cho.jpg',
    'kim': 'img/kim.jpg',
    'lee': 'img/lee.jpg',
    'koo' : 'img/koo.jpg'
}

missing_info={
    'chan':"man,2006-06-21,have a unbalance chik",
    'sunny':"women,2014-06-30,pretty"
}

descs = {
    'chan':None,
    'sunny':None,
    'cho': None,
    'choi': None,
    'kim':None,
    'lee': None,
    'koo': None

}

for name, img_path in img_paths.items():
    img_bgr=cv2.imread(img_path)
    img_rgb=cv2.cvtColor(img_bgr,cv2.COLOR_BGR2RGB)

    _, img_shapes, _= find_faces(img_rgb)
    descs[name] = encode_faces(img_rgb,img_shapes)[0]

np.save('img/descs.npy',descs)

print(descs,file=sys.stderr)
target='img/chan.jpg'
img_bgr = cv2.imread(target)
#sunnytest, currentchan
img_rgb = cv2.cvtColor(img_bgr, cv2.COLOR_BGR2RGB)

rects, shapes, _ = find_faces(img_rgb)
descriptors = encode_faces(img_rgb, shapes)


for i, desc in enumerate(descriptors):

    found = False
    mdist=99.0
    for name, saved_desc in descs.items():
        dist = np.linalg.norm([desc]-saved_desc, axis=1)
        if dist < 0.5:
            if(dist<mdist):
                found = True
                mname=name
                mdist=dist
                fig, ax = plt.subplots(1, figsize=(20, 20))
                ax.imshow(img_rgb)
                text = ax.text(rects[i][0][0], rects[i][0][1], mname,
                               color='b', fontsize=40, fontweight='bold')
                text.set_path_effects([path_effects.Stroke(linewidth=10, foreground='white'), path_effects.Normal()])
                rect = patches.Rectangle(rects[i][0],
                                         rects[i][1][1] - rects[i][0][1],
                                         rects[i][1][0] - rects[i][0][0],
                                         linewidth=2, edgecolor='w', facecolor='none')
                ax.add_patch(rect)







    if not found:
        ax.text(rects[i][0][0], rects[i][0][1], 'unknown',
                color='r', fontsize=20, fontweight='bold')
        rect = patches.Rectangle(rects[i][0],
                                 rects[i][1][1] - rects[i][0][1],
                                 rects[i][1][0] - rects[i][0][0],
                                 linewidth=2, edgecolor='r', facecolor='none')
        ax.add_patch(rect)

print(missing_info[mname])

plt.axis('off')
plt.savefig('result/output.png')
plt.show()



solution_img=img_paths[mname]
img_bgr2 = cv2.imread(solution_img)
img_rgb2 = cv2.cvtColor(img_bgr2, cv2.COLOR_BGR2RGB)
fig2, ax2 = plt.subplots(1, figsize=(20, 20))
ax2.imshow(img_rgb2)
plt.savefig('result/solution.png')
